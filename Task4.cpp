//============================================================================
// Name        : Task4.cpp
// Author      : Mohamed Asran
// Description : Penny Distribution Machine
//============================================================================

#include <iostream>
#include <vector>
using namespace std;

/* Using greedy algorithm */
vector<int> pennyMachine(int n)
{/* n represents the number of pennies */

	int index=0;
	vector<int> result;

	/* Put the number of in the first box */
	result.push_back(n);

	/* Iterate on each box if it's greater than 1
	 * and Divide the value in the box by 2.
	 * Put the remainder in the same box
	 * and the division result in the next box */
	do{
		result.push_back(result.at(index) / 2);
		result.at(index) = result.at(index) % 2;
		index++;
	}while(result.at(index) > 1);

	/* Return the result boxes */
	return result;
}

/* Using brute force algorithm */
vector<int> pennyMachine2(int n)
{/* n represents the number of pennies */

	int index=0;
	int nextIndex = 0;
	vector<int> result;

	/* Put the number of in the first box */
	result.push_back(n);

	/* Iterate on each box if it's greater than 1
	 * and subtract the value in the box by 2.
	 * increase the value of the next box by 1
	 * and continue till the box value becomes 0 or 1 */
	while(result.at(index) > 1)
	{
		result.at(index) -= 2;
		nextIndex++;
		if(result.at(index) <=1){
			result.push_back(nextIndex);
			nextIndex = 0;
			index++;
		}
	}

	/* Return the result boxes */
	return result;
}

int main()
{
	/* Ask the user for the number of pennies */
	int pennies=0;
	cout << "Enter number of pennies: ";
	cin >> pennies;

	vector<int> boxes = pennyMachine(pennies);

	/* Print the final boxes distribution */
	int length = boxes.size();
	for(int j=0; j<length; j++)
	{
		cout << boxes.at(j) << " ";
	}
}
