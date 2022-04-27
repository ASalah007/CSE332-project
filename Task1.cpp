#include <bits/stdc++.h>
#include <chrono>
#include <thread>
#define vi vector<int>
#define endl "\n"
#define pb push_back
#define pf push_front
#define leftC 0
#define rightC 1
#define topC 2
using namespace std;
using namespace this_thread;
using namespace chrono;
void printTriangle(vector<deque<int>>& graph,char symbol)
{
    for (size_t i = 0; i < graph.size(); i++)
    {
        int curr = 0;
        for (auto x : graph[i])
        {
            while (x != curr)
            {
				cout << " ";
				curr++;
            }
            cout << symbol;
            curr++;
        }
        cout << endl;
    }
}
void init(vector <deque<int>>& v, int rows)
{
    int cols = 2 * rows - 1;
    deque<int> even, odd;
    even.pb(rows - 1);
    odd.pb(rows - 2);
    odd.pb(rows);
    for (int i = 0; i < rows; i++)
    {
        if (i & 1)
        {
            for(auto x:odd)
            {
                v[i].pb(x);
            }
            odd.pf(*odd.begin() - 2);
            odd.pb(*odd.rbegin() + 2);
        }
        else
        {
            for (auto x : even)
            {
                v[i].pb(x);
            }
            even.pf(*even.begin() - 2);
            even.pb(*even.rbegin() + 2);
        }
    }
}
int sum(int x)
{
    return x * (x + 1) / 2;
}
void options(bool& steps, int& delay, char& symbol)
{
    bool flag = 1;
    while (flag)
    {
        system("cls");
        cout << "1. Step by Step Simulation: " << steps << endl;
        cout << "2. Delay Between Steps: " << delay << "ms" << endl;
		cout << "3. Symbol: " << symbol << endl;
        cout << "4. Back to Main Menu" << endl;

        int choice;
        cin >> choice;
        switch (choice)
        {
        case 1:
            steps = !steps;
            break;
        case 2:
            cout << "Enter delay in ms: ";
            int temp;
            cin >> temp;
            delay = max(temp, 0);
            break;
        case 3:
			cout << "Enter symbol: ";
			cin >> symbol;
			break;
        case 4:
            flag = 0;
            break;
        default:
            cout << "Invalid choice" << endl;
            system("Pause");
            break;
        }
    }
}
void mainmenu(bool& simulation, int& delay, char& symbol)
{
    bool flag = 1;
    while (flag)
    {
        system("cls");
        cout << "1. Start" << endl;
        cout << "2. Options" << endl;
        cout << "3. Exit" << endl;
        int choice;
        cin >> choice;
        switch (choice)
        {
        case 1:
            flag = 0;
            break;
        case 2:
            options(simulation, delay, symbol);
            break;
        case 3:
            exit(0);
            break;
        default:
            cout << "Invalid choice" << endl;
            system("Pause");
            break;
        }
    }

}
int main()
{
    bool simulation = 1;// step by step = 1, final result only = 0
    int delay = 1000;// in ms    
    char symbol = '*';// Symbol used to print the triangle
	streambuf* coutbuf = cout.rdbuf();
    while (true)
    {
        mainmenu(simulation, delay, symbol);
        cout << "Enter the number of rows: ";
        int steps = 0;
        int n;
        cin >> n;
        while (n < 1)
        {
            cout << "Please enter a positive number: ";
            cin >> n;
            continue;
        }
        vi removed(3);//number of rows to be removed from Left,Right,Top corners respectively
        removed[leftC] = (n + 1) / 3;//left corner rows to be moved
        removed[rightC] = (n) / 3;//right corner rows to be moved
        removed[topC] = (n - 1) / 3;//top corner rows to be moved
        vector<deque<int>> graph(n + removed[topC]);
        init(graph, n);
        if (simulation)printTriangle(graph, symbol);
        cout << endl;
        for (int i = removed[topC] - 1, j = 0; i >= 0; i--, j++)
        {
            if (!j)cout << "rotating the top rows\n\n";
            auto top = graph[i].begin();
            auto bottom = graph[n - 1 + j].begin();
            if (removed[topC] - 1 == i)advance(bottom, removed[leftC]);
            while (top != graph[i].end())
            {
                int temp = *bottom + 1;
                //Star temp(Star(*bottom).getX() + 1, Star(*bottom).getY() + 1);
                top = graph[i].erase(top);
                graph[n + j].pb(temp);
                advance(bottom, 1);
                if (simulation)printTriangle(graph, symbol);
                cout << "Steps = " << ++steps << endl;
                if (simulation)sleep_for(milliseconds(delay));
            }
        }
        //rotating left rows(removed[leftC]) and moving them to top right
        for (int i = removed[leftC] - 1, j = 0; i >= 0; i--, j++)
        {
            if (!j)cout << "rotating the left rows\n\n";
            auto top = graph[removed[topC] + j].rbegin();
            auto left = graph[n - 1 - j].begin();
            int k = 0;
            while (k++ < removed[leftC] - j)
            {
                int temp = *top + 2;
                //Star temp(Star(*top).getX(), Star(*top).getY() + 2);
                left = graph[n - 1 - j].erase(left);
                graph[removed[topC] + j].pb(temp);
                top = graph[removed[topC] + j].rbegin();
                if (simulation)printTriangle(graph, symbol);
                cout << "Steps = " << ++steps << endl;
                if (simulation)sleep_for(milliseconds(delay));
            }
        }
        //rotating right rows(removed[rightC]) and moving them to top left
        for (int i = removed[rightC] - 1, j = 0; i >= 0; i--, j++)
        {
            if (!j)cout << "rotating the right rows\n\n";
            auto top = graph[removed[topC] + j].begin();
            auto right = prev(graph[n - 1 - j].end());
            int k = 0;
            while (k++ < removed[rightC] - j)
            {
                int temp = *top - 2;
                //Star temp(Star(*top).getX(), Star(*top).getY() - 2);
                right = prev(graph[n - 1 - j].erase(right));
                graph[removed[topC] + j].pf(temp);
                top = graph[removed[topC] + j].begin();
                if (simulation)printTriangle(graph, symbol);
                cout << "Steps = " << ++steps << endl;
                if (simulation)sleep_for(milliseconds(delay));
            }
        }
        if (!steps)cout << "*\n";
        cout << "Number of steps = " << sum(removed[leftC]) << " + " << sum(removed[topC]) << " + " << sum(removed[rightC]) << " = " << steps << endl;
        system("pause");
        cout.rdbuf(coutbuf);
    }
}