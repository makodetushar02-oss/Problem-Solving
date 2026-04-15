from collections import deque

def read_grid():
    import sys
    data = sys.stdin.read().strip().split()
    M, N = map(int, data[:2])
    cells = data[2:]
    # Input lines may be space-separated characters; build grid
    grid = [cells[i*N:(i+1)*N] for i in range(M)]
    return M, N, grid

def solve():
    M, N, grid = read_grid()

    def inb(r, c): return 0 <= r < M and 0 <= c < N
    def free(r, c): return inb(r, c) and grid[r][c] != 'H'

    # Collect start ('s') and target ('S') cells
    s_cells, S_cells = [], []
    for r in range(M):
        for c in range(N):
            if grid[r][c] == 's':
                s_cells.append((r, c))
            elif grid[r][c] == 'S':
                S_cells.append((r, c))

    if len(s_cells) != 2 or len(S_cells) != 2:
        print(-1)
        return

    s_cells.sort()
    S_cells.sort()
    S_set = tuple(S_cells)  # sorted

    # Verify both pairs are adjacent (problem states sofa always occupies two cells)
    def adjacent(p, q):
        return abs(p[0]-q[0]) + abs(p[1]-q[1]) == 1

    if not adjacent(*s_cells) or not adjacent(*S_cells):
        print(-1)
        return

    start = tuple(s_cells)

    # Helper: generate neighbors
    def neighbors(state):
        (r1, c1), (r2, c2) = state
        # Slides (4 dirs)
        for dr, dc in ((1,0),(-1,0),(0,1),(0,-1)):
            nr1, nc1 = r1+dr, c1+dc
            nr2, nc2 = r2+dr, c2+dc
            if free(nr1, nc1) and free(nr2, nc2):
                yield tuple(sorted(((nr1, nc1), (nr2, nc2))))

        # Rotations inside clear 2x2s
        # Horizontal?
        if r1 == r2 and abs(c1 - c2) == 1:
            r, cL, cR = r1, min(c1, c2), max(c1, c2)
            # rotate up
            ru = r - 1
            if ru >= 0 and all(free(rr, cc) for rr in (ru, r) for cc in (cL, cR)):
                yield tuple(sorted(((ru, cL), (r, cL))))
                yield tuple(sorted(((ru, cR), (r, cR))))
            # rotate down
            rd = r + 1
            if rd < M and all(free(rr, cc) for rr in (r, rd) for cc in (cL, cR)):
                yield tuple(sorted(((r, cL), (rd, cL))))
                yield tuple(sorted(((r, cR), (rd, cR))))
        # Vertical?
        elif c1 == c2 and abs(r1 - r2) == 1:
            c, rT, rB = c1, min(r1, r2), max(r1, r2)
            # rotate left
            cl = c - 1
            if cl >= 0 and all(free(rr, cc) for rr in (rT, rB) for cc in (cl, c)):
                yield tuple(sorted(((rT, cl), (rT, c))))
                yield tuple(sorted(((rB, cl), (rB, c))))
            # rotate right
            cr = c + 1
            if cr < N and all(free(rr, cc) for rr in (rT, rB) for cc in (c, cr)):
                yield tuple(sorted(((rT, c), (rT, cr))))
                yield tuple(sorted(((rB, c), (rB, cr))))

    # BFS
    q = deque([(start, 0)])
    seen = {start}
    goal = tuple(S_set)

    while q:
        state, d = q.popleft()
        if state == goal:
            print(d)
            return
        for nb in neighbors(state):
            if nb not in seen:
                seen.add(nb)
                q.append((nb, d+1))

    print(-1)

if __name__ == "__main__":
    solve()
