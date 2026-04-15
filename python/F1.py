# Read from stdin, output minimum number of cars
# Usage: provide N then N lines each "x y day"
import sys

def manhattan(a, b):
    return abs(a[0]-b[0]) + abs(a[1]-b[1])

def dfs(u, used, matchR, adj):
    for v in adj[u]:
        if used[v]:
            continue
        used[v] = True
        if matchR[v] == -1 or dfs(matchR[v], used, matchR, adj):
            matchR[v] = u
            return True
    return False

def max_bipartite_matching(adj, n):
    # left vertices 0..n-1, right vertices 0..n-1
    matchR = [-1]*n
    result = 0
    for u in range(n):
        used = [False]*n
        if dfs(u, used, matchR, adj):
            result += 1
    return result

def main():
    data = sys.stdin.read().strip().split()
    if not data:
        return
    it = iter(data)
    n = int(next(it))
    races = []
    for _ in range(n):
        x = int(next(it)); y = int(next(it)); d = int(next(it))
        races.append((x,y,d))
    # adjacency from left i to right j if race j can follow i
    adj = [[] for _ in range(n)]
    for i in range(n):
        xi, yi, di = races[i]
        for j in range(n):
            xj, yj, dj = races[j]
            if di < dj:
                dist = abs(xi - xj) + abs(yi - yj)
                if dist <= dj - di:
                    adj[i].append(j)
    max_match = max_bipartite_matching(adj, n)
    min_cars = n - max_match
    print(min_cars)

if _name_ == "_main_":
    main()