import pandas as pd
import numpy as np

def calculate_log_likelihood(n, k):
    """
    Calculates log-likelihood for a single bucket to minimize variance
    and maximize predictive power for defaults.
    """
    if n == 0:
        return 0
    p = k / n
    # Handle edge cases where probability is 0 or 1
    if p == 0 or p == 1:
        return 0 
    
    return k * np.log(p) + (n - k) * np.log(1 - p)

def optimize_fico_buckets(data, num_buckets):
    """
    Uses dynamic programming to find optimal bucket boundaries.
    """
    unique_ficos = data['fico_score'].unique()
    num_unique = len(unique_ficos)
    
    # Initialize Dynamic Programming tables
    dp = np.full((num_unique + 1, num_buckets + 1), -np.inf)
    pointers = np.zeros((num_unique + 1, num_buckets + 1), dtype=int)
    dp[0][0] = 0

    # Fast range queries via cumulative defaults and counts
    fico_counts = data.groupby('fico_score')['default'].agg(['count', 'sum']).reset_index()
    cum_n = np.concatenate(([0], fico_counts['count'].cumsum().values))
    cum_k = np.concatenate(([0], fico_counts['sum'].cumsum().values))

    # Populate the DP table
    for i in range(1, num_unique + 1):
        for j in range(1, min(i, num_buckets) + 1):
            for split in range(j - 1, i):
                n_bucket = cum_n[i] - cum_n[split]
                k_bucket = cum_k[i] - cum_k[split]
                
                ll = calculate_log_likelihood(n_bucket, k_bucket)
                
                # Check for optimal log-likelihood
                if dp[split][j - 1] + ll > dp[i][j]:
                    dp[i][j] = dp[split][j - 1] + ll
                    pointers[i][j] = split
                    
    # Backtrack to identify the actual FICO score boundaries
    boundaries = []
    curr_idx = num_unique
    for j in range(num_buckets, 0, -1):
        split_idx = pointers[curr_idx][j]
        boundaries.append(unique_ficos[split_idx])
        curr_idx = split_idx
        
    boundaries.reverse()
    
    # Return mapping ranges
    return [-np.inf] + boundaries[1:] + [np.inf]

if __name__ == "__main__":
    print("Initializing FICO Score Quantization...")
    
    try:
        # Load data
        df = pd.read_csv('customer_loan_data.csv')
        fico_data = df[['fico_score', 'default']].sort_values(by='fico_score').reset_index(drop=True)
        
        # Process 5 Buckets as requested
        num_buckets = 5
        optimal_boundaries = optimize_fico_buckets(fico_data, num_buckets)
        print(f"Algorithm Complete. Optimal Boundaries: {optimal_boundaries}")
        
        # Map back to original dataframe
        df['fico_rating'] = pd.cut(df['fico_score'], bins=optimal_boundaries, labels=range(1, num_buckets + 1))
        df.to_csv('customer_loan_data_mapped.csv', index=False)
        print("Successfully exported mapped data to 'customer_loan_data_mapped.csv'")
        
    except FileNotFoundError:
        print("Error: 'customer_loan_data.csv' not found in the current directory. Please ensure the data file is present.")