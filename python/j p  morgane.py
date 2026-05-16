import pandas as pd
import numpy as np
from datetime import datetime
from sklearn.linear_model import LinearRegression

# 1. Load and prepare the data
data = {
    'Dates': ['10/31/20', '11/30/20', '12/31/20', '1/31/21', '2/28/21', '3/31/21', '4/30/21', '5/31/21', '6/30/21', '7/31/21', '8/31/21', '9/30/21',
              '10/31/21', '11/30/21', '12/31/21', '1/31/22', '2/28/22', '3/31/22', '4/30/22', '5/31/22', '6/30/22', '7/31/22', '8/31/22', '9/30/22',
              '10/31/22', '11/30/22', '12/31/22', '1/31/23', '2/28/23', '3/31/23', '4/30/23', '5/31/23', '6/30/23', '7/31/23', '8/31/23', '9/30/23',
              '10/31/23', '11/30/23', '12/31/23', '1/31/24', '2/29/24', '3/31/24', '4/30/24', '5/31/24', '6/30/24', '7/31/24', '8/31/24', '9/30/24'],
    'Prices': [10.1, 10.3, 11.0, 10.9, 10.9, 10.9, 10.4, 9.84, 10.0, 10.1, 10.3, 10.2, 10.1, 11.2, 11.4, 11.5, 11.8, 11.5, 10.7, 10.7, 10.4, 10.5, 10.4, 10.8,
               11.0, 11.6, 11.6, 12.1, 11.7, 12.0, 11.5, 11.2, 10.9, 11.4, 11.1, 11.5, 11.8, 12.2, 12.8, 12.6, 12.4, 12.7, 12.1, 11.4, 11.5, 11.6, 11.5, 11.8]
}

df = pd.DataFrame(data)
df['Dates'] = pd.to_datetime(df['Dates'])
df['Days'] = (df['Dates'] - df['Dates'].min()).dt.days
df['Month'] = df['Dates'].dt.month

# 2. Model the trend using Linear Regression
model = LinearRegression().fit(df[['Days']], df['Prices'])

# 3. Calculate seasonal adjustment (avg deviation from trend for each month)
df['Trend'] = model.predict(df[['Days']])
df['Seasonal_Diff'] = df['Prices'] - df['Trend']
seasonal_map = df.groupby('Month')['Seasonal_Diff'].mean()

def get_gas_price(input_date_str):
    """
    Estimates the price of natural gas for a given date.
    Input: 'YYYY-MM-DD'
    """
    date = pd.to_datetime(input_date_str)
    days_since_start = (date - df['Dates'].min()).days
    
    # Calculate trend price
    trend_price = model.predict([[days_since_start]])[0]
    
    # Apply seasonal adjustment
    adjustment = seasonal_map[date.month]
    
    return round(trend_price + adjustment, 2)

# Examples:
print(f"Price on 2021-06-15: ${get_gas_price('2021-06-15')}")
print(f"Projected price on 2025-01-15 (Winter): ${get_gas_price('2025-01-15')}")
print(f"Projected price on 2025-07-15 (Summer): ${get_gas_price('2025-07-15')}")