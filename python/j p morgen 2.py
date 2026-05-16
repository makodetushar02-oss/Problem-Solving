import pandas as pd
from datetime import datetime

# 1. Load the Provided Data
data = {
    'Dates': [
        '10/31/20', '11/30/20', '12/31/20', '1/31/21', '2/28/21', '3/31/21', '4/30/21', '5/31/21', '6/30/21', '7/31/21', '8/31/21', '9/30/21',
        '10/31/21', '11/30/21', '12/31/21', '1/31/22', '2/28/22', '3/31/22', '4/30/22', '5/31/22', '6/30/22', '7/31/22', '8/31/22', '9/30/22',
        '10/31/22', '11/30/22', '12/31/22', '1/31/23', '2/28/23', '3/31/23', '4/30/23', '5/31/23', '6/30/23', '7/31/23', '8/31/23', '9/30/23',
        '10/31/23', '11/30/23', '12/31/23', '1/31/24', '2/29/24', '3/31/24', '4/30/24', '5/31/24', '6/30/24', '7/31/24', '8/31/24', '9/30/24'
    ],
    'Prices': [
        10.1, 10.3, 11.0, 10.9, 10.9, 10.9, 10.4, 9.84, 10.0, 10.1, 10.3, 10.2,
        10.1, 11.2, 11.4, 11.5, 11.8, 11.5, 10.7, 10.7, 10.4, 10.5, 10.4, 10.8,
        11.0, 11.6, 11.6, 12.1, 11.7, 12.0, 11.5, 11.2, 10.9, 11.4, 11.1, 11.5,
        11.8, 12.2, 12.8, 12.6, 12.4, 12.7, 12.1, 11.4, 11.5, 11.6, 11.5, 11.8
    ]
}

df = pd.DataFrame(data)
df['Dates'] = pd.to_datetime(df['Dates'])

def get_price_for_date(target_date_str):
    """
    Finds the price for a specific date. 
    If the exact date isn't in the dataset, it finds the closest available date.
    """
    target_date = pd.to_datetime(target_date_str)
    # Find the index of the closest date in our dataframe
    nearest_idx = (df['Dates'] - target_date).abs().idxmin()
    return df.loc[nearest_idx, 'Prices']

# 2. The Pricing Model
def price_storage_contract(injections, withdrawals, rate, max_volume, storage_cost_per_day):
    """
    Calculates the net value of a natural gas storage contract.
    
    Parameters:
    - injections: List of tuples -> [('YYYY-MM-DD', volume_to_inject), ...]
    - withdrawals: List of tuples -> [('YYYY-MM-DD', volume_to_withdraw), ...]
    - rate: Maximum volume that can be injected/withdrawn per day.
    - max_volume: Maximum total capacity of the storage facility.
    - storage_cost_per_day: Cost to store 1 unit of gas for 1 day ($).
    """
    
    # Consolidate and sort all actions chronologically
    actions = []
    for date, vol in injections:
        actions.append({'date': pd.to_datetime(date), 'type': 'inject', 'vol': vol})
    for date, vol in withdrawals:
        actions.append({'date': pd.to_datetime(date), 'type': 'withdraw', 'vol': vol})
        
    actions.sort(key=lambda x: x['date'])
    
    inventory = 0
    total_cash_flow = 0
    last_date = None
    
    for action in actions:
        current_date = action['date']
        
        # 1. Deduct storage costs incurred since the last action
        if last_date is not None:
            days_passed = (current_date - last_date).days
            cost = days_passed * inventory * storage_cost_per_day
            total_cash_flow -= cost
            
        # 2. Get market price for the current action
        price = get_price_for_date(current_date)
        
        # 3. Process the physical action and enforce constraints
        intended_vol = action['vol']
        
        # Enforce rate constraint (cannot move more than 'rate' at a time)
        actual_vol = min(intended_vol, rate)
        
        if action['type'] == 'inject':
            # Enforce max volume constraint
            if inventory + actual_vol > max_volume:
                actual_vol = max_volume - inventory 
                print(f"Warning on {current_date.date()}: Reached max capacity. Only injected {actual_vol}.")
                
            inventory += actual_vol
            # Buying gas = cash outflow
            total_cash_flow -= actual_vol * price 
            
        elif action['type'] == 'withdraw':
            # Enforce empty storage constraint
            if actual_vol > inventory:
                actual_vol = inventory
                print(f"Warning on {current_date.date()}: Not enough inventory. Only withdrew {actual_vol}.")
                
            inventory -= actual_vol
            # Selling gas = cash inflow
            total_cash_flow += actual_vol * price 
            
        last_date = current_date
        
    return total_cash_flow

# 3. Test the Model with the provided data constraints
# Scenario: 
# - Inject 10,000 units during a cheap summer month (May 2021, Price: $9.84)
# - Withdraw 10,000 units during an expensive winter month (Dec 2021, Price: $11.40)
# - Max capacity is 50,000, Max rate is 15,000 per action, Storage costs $0.002 per unit per day.

injections = [('2021-05-31', 10000)]
withdrawals = [('2021-12-31', 10000)]

contract_value = price_storage_contract(
    injections=injections,
    withdrawals=withdrawals,
    rate=15000,
    max_volume=50000,
    storage_cost_per_day=0.002
)

print(f"Total Contract Value (Profit/Loss): ${contract_value:,.2f}")