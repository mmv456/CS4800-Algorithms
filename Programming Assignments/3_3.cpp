#include <stdio.h>
#include <vector>
#include <algorithm>

using namespace std;

int V, E;
vector<vector<int> > G;
vector<vector<int> > GD;
vector<vector<int> > res;
vector<vector<int> > SCCconn;

vector<int> sortOrder;

bool *marked;
int *reg;
int groupNo;

void dfs(int vertex) {
	if (marked[vertex])return;
	marked[vertex] = true;

	for (int i = 0; i<G[vertex].size(); ++i) dfs(G[vertex][i]);
	sortOrder.push_back(vertex);
}

void dfs2(int vertex) {
	if (marked[vertex])return;
	marked[vertex] = true;

	for (int i = 0; i<GD[vertex].size(); ++i)dfs2(GD[vertex][i]);
	res[groupNo].push_back(vertex);
	reg[vertex] = groupNo;
}

void dfs3(int callingGroup, int vertex) {
	if (marked[vertex]) {
		if (callingGroup != reg[vertex])SCCconn[callingGroup].push_back(reg[vertex]);
		return;
	}
	marked[vertex] = true;
	for (int i = 0; i<GD[vertex].size(); ++i) {
		dfs3(callingGroup, GD[vertex][i]);
	}
}

int dfs4(int callingGroup) {
	int size = 0;
	for (int i = 0; i<SCCconn[callingGroup].size(); ++i) {
		size += dfs4(SCCconn[callingGroup][i]);
	}
	size += res[callingGroup].size();
	return size;
}

int main() {
	scanf("%d %d", &V, &E);
	marked = new bool[V];
	reg = new int[V];

	for (int i = 0; i<V; ++i)marked[i] = false;
	for (int i = 0; i<V; ++i)reg[i] = -1;
	groupNo = -1;

	for (int i = 0; i<V; ++i) {
		vector<int> x, xx, xxx, xxxx;
		res.push_back(x);
		G.push_back(xx);
		GD.push_back(xxx);
		SCCconn.push_back(xxxx);
	}

	for (int i = 0; i<E; ++i) {
		int from, to;
		scanf("%d %d", &from, &to);
		from--; to--;
		G[from].push_back(to);
		GD[to].push_back(from);
	}

	if (V == 1) {
		printf("%d\n%d", 1, V);
		return 0;
	}
	for (int i = 0; i<V; ++i)dfs(i);
	for (int i = 0; i<V; ++i)marked[i] = false;
	for (int i = sortOrder.size() - 1; i >= 0; --i) {
		if (!marked[sortOrder[i]])groupNo++;
		dfs2(sortOrder[i]);
	}
	vector<int> result;
	for (int i = 0; i<V; ++i)marked[i] = false;
	for (int i = 0; i<V; ++i) {
		if (res[i].size() == 0)break;
		for (int j = 0; j<res[i].size(); ++j) {
			dfs3(i, res[i][j]);
		}
	}

	for (int i = 0; i<SCCconn.size(); ++i) {
		int sizeOfGroup = dfs4(i);
		if (sizeOfGroup == V) {
			for (int j = 0; j<res[i].size(); ++j) {
				result.push_back(res[i][j]);
			}
		}
	}
	printf("%d\n", result.size());
	sort(result.begin(), result.end());

	for (int i = 0; i<result.size(); ++i)printf("%d ", result[i] + 1);
	printf("\n");
}