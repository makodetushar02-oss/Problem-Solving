import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier

# 1. Load the Data
# Ensure the dataset you pasted is saved in the same folder as 'customer_loan_data.csv'
df = pd.read_csv('customer_loan_data.csv')

# 2. Prepare Features (X) and Target (y)
# Drop the customer_id as it is an arbitrary identifier
X = df.drop(columns=['customer_id', 'default']) 
y = df['default']

# Split into training and testing sets (80% train, 20% test)
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# 3. Train the Model
# We use Random Forest here as it performs excellently out-of-the-box on tabular financial data
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X_train, y_train)

# 4. Define the Required Output Function
def calculate_expected_loss(customer_data_dict):
    """
    Takes in the properties of a loan customer and outputs the expected financial loss.
    """
    # Convert the single customer dictionary into a format the model can read
    customer_df = pd.DataFrame([customer_data_dict])
    
    # predict_proba returns an array of probabilities: [Prob_of_0, Prob_of_1]
    # We want index 1, which represents the Probability of Default (PD)
    pd_estimate = model.predict_proba(customer_df)[0][1]
    
    # Calculate Expected Loss
    # JPM recovers 10%, meaning the Loss Given Default (LGD) is 90% (0.90)
    loan_amount = customer_data_dict['loan_amt_outstanding']
    expected_loss = pd_estimate * 0.90 * loan_amount
    
    return expected_loss

# --- Example Evaluation ---
# Testing the function using the second row from your provided data (Customer 7442532)
sample_customer = {
    'credit_lines_outstanding': 5,
    'loan_amt_outstanding': 1958.928726,
    'total_debt_outstanding': 8228.75252,
    'income': 26648.43525,
    'years_employed': 2,
    'fico_score': 572
}

estimated_loss = calculate_expected_loss(sample_customer)
print(f"The Expected Loss for this specific loan is: ${estimated_loss:.2f}")