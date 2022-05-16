#include<bits/stdc++.h>

#define ll long long
#define IO	ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0);
#define oo LLONG_MAX

using namespace std;

int index = 1;
ll disks = 0;
vector<pair<ll, ll>> reveDp;

pair<ll, ll> getRevePair(ll n) {
	//base case
	if (n == 0)
		return reveDp[n] = { 0,-1 };
	if (n == 1)
		return reveDp[n] = { 1,-1 };
	if (n == 2)
		return reveDp[n] = { 3,-1 };
	//transition
	if (reveDp[n].first != -1) {
		return reveDp[n];
	}
	ll ans = oo;
	int fourPegsDisks = -1;
	for (ll i = 1; i < n; ++i) {
		ll choice2 = (2 * getRevePair(i).first + pow(2, n - i) - 1);
		if (choice2 < ans) {
			ans = choice2;
			fourPegsDisks = i;
		}
	}
	return reveDp[n] = { ans,fourPegsDisks };
}


void hanoiTransfer(int n, char from, char to, char aux, int offset) {
	//base case
	if (n == 0)
		return;
	//transition
	hanoiTransfer(n - 1, from, aux, to, offset);
	cout << index++ << "- Move Disk number " + to_string(n + offset) + " From " + from + " to " + to << endl;
	hanoiTransfer(n - 1, aux, to, from, offset);
}

void reveTransfer(int n, char from, char to, char aux1, char aux2) {
	//base case
	if (n == 0)
		return;
	if (n == 1) {
		cout << index++ << "- Move Disk number " + to_string(n) + " From " + from + " to " + to << endl;
		return;
	}
	if (n == 2) {
		cout << index++ << "- Move Disk number " + to_string(n - 1) + " From " + from + " to " + aux1 << endl;
		cout << index++ << "- Move Disk number " + to_string(n) + " From " + from + " to " + to << endl;
		cout << index++ << "- Move Disk number " + to_string(n - 1) + " From " + aux1 + " to " + to << endl;
		return;
	}
	//transition
	reveTransfer(reveDp[n].second, from, aux2, aux1, to);
	hanoiTransfer(n - reveDp[n].second, from, to, aux1, reveDp[n].second);
	reveTransfer(reveDp[n].second, aux2, to, aux1, from);
}

void solve() {
	reveDp.resize(disks + 1, { -1,-1 });
	pair<ll, ll> revePair = getRevePair(disks);
	cout << "The number of steps = " << revePair.first << endl;
	reveTransfer(disks, 'A', 'D', 'B', 'C');
}

// driver program
int main() {
	IO;
	cin >> disks;
	solve();
}


