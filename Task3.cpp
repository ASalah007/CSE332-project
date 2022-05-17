#include <bits/stdc++.h>

using namespace std;

bool marked[4][3]={false};

int cnt=0;
class knight{

public:
    string name;
    pair<int,int> position;
    pair<int,int> targetPosition;
    bool arrived=false;
    bool visits[4][3]={false};
    knight(){

    }
    knight(string name,pair<int,int> position , pair<int,int>targetPosition){
        this->name=name;
        this->position=position;
        this->targetPosition=targetPosition;
    }
    void arrivalChecker(){
        if(this->position==this->targetPosition){
            this->arrived=true;
        }
    }

    vector<pair<int,int>> knightMoveList(){
        vector<pair<int,int>> moveList;
        int r=this->position.first;
        int c=this->position.second;
        if(r+1<4 && c+2<3 && (marked[r+1][c+2]==false) && (this->visits[r+1][c+2]==false) ){
            moveList.push_back(make_pair(r+1,c+2));
        }
        if(r+2<4 && c+1<3 && (marked[r+2][c+1]==false) && (this->visits[r+2][c+1]==false) ){
            moveList.push_back(make_pair(r+2,c+1));
        }
        if(r-1>=0 && c+2<3 && (marked[r-1][c+2]==false) && (this->visits[r-1][c+2]==false) ){
            moveList.push_back(make_pair(r-1,c+2));
        }
        if(r+2<4 && c-1>=0 && (marked[r+2][c-1]==false) && (this->visits[r+2][c-1]==false) ){
            moveList.push_back(make_pair(r+2,c-1));
        }
        if(r+1<4 && c-2>=0 && (marked[r+1][c-2]==false) && (this->visits[r+1][c-2]==false) ){
            moveList.push_back(make_pair(r+1,c-2));
        }
        if(r-2>=0 && c+1<3 && (marked[r-2][c+1]==false) && (this->visits[r-2][c+1]==false) ){
            moveList.push_back(make_pair(r-2,c+1));
        }
        if(r-2>=0 && c-1>=0 && (marked[r-2][c-1]==false) && (this->visits[r-2][c-1]==false) ){
            moveList.push_back(make_pair(r-2,c-1));
        }
        if(r-1>=0 && c-2>=0 && (marked[r-1][c-2]==false) && (this->visits[r-1][c-2]==false) ){
            moveList.push_back(make_pair(r-1,c-2));
        }
        return moveList;
    }
    void move(pair<int,int> newPosition){
        if(!(this->arrived)){
            marked[newPosition.first][newPosition.second]=true;
            marked[this->position.first][this->position.second]=false;
            visits[this->position.first][this->position.second]=true;
            this->position=newPosition;
            this->arrivalChecker();
            cnt++;
        }
    }

};

void simulate(knight whiteKnights[3] , knight blackKnights[3]){
    string arr[4][3]={{"00","00","00"} , {"00","00","00"} ,  {"00","00","00"}, {"00","00","00"} };
    for(int i=0; i<3; i++){
        arr[whiteKnights[i].position.first][whiteKnights[i].position.second]=whiteKnights[i].name;
        arr[blackKnights[i].position.first][blackKnights[i].position.second]=blackKnights[i].name;
    }
    for(int i=0; i<4; i++){
        for(int j=0; j<3; j++){
            cout << arr[i][j] << "   ";
        }
        cout << endl;
    }
    cout << "#################################"<< endl;

}

bool blackMove(const pair<int,int> &a , const pair<int,int> &b){
    if(a.second %2 ==0 && b.second%2==0){
        return b.first < a.first;
    }
    if(a.second %2==0){
        return true;
    }
    return false;

}
bool whiteMove(const pair<int,int> &a , const pair<int,int> &b){
    if(a.second %2 ==0 && b.second%2==0){
        return a.first < b.first;
    }
    if(a.second %2==0){
        return true;
    }
    return false;

}

bool solved(knight whiteKnights[3],knight blackKnights[3]){
    for(int i=0; i<3; i++){
        if( !(whiteKnights[i].arrived) || !(blackKnights[i].arrived)){
            return false;
        }
    }
    return true;
}

void Invade(knight whiteKnights[3] , knight blackKnights[3]){
    if(solved(whiteKnights,blackKnights)){
        simulate(whiteKnights,blackKnights);
        return;
    }
    simulate(whiteKnights,blackKnights);
    for(int i=0; i<3; i++){

        vector<pair<int,int>> whiteMoves=whiteKnights[i].knightMoveList();
        sort(whiteMoves.begin(),whiteMoves.end(),whiteMove);
        if(whiteMoves.size()>0 )
            whiteKnights[i].move(whiteMoves[0]);
        vector<pair<int,int>> blackMoves=blackKnights[i].knightMoveList();
        sort(blackMoves.begin(),blackMoves.end(),blackMove);
        if(blackMoves.size()>0 )
            blackKnights[i].move(blackMoves[0]);
    }
    Invade(whiteKnights,blackKnights);

}

void solution(knight whiteKnights[3] ,knight blackKnights[3] ){

    //initializing knights
    for(int i=0; i<3; i++){
        whiteKnights[i].name="W"+to_string(i);
        blackKnights[i].name="B"+to_string(i);
        whiteKnights[i].position=make_pair(3,i);
        blackKnights[i].position=make_pair(0,i);
        whiteKnights[i].visits[3][i]=true;
        blackKnights[i].visits[0][i]=true;
        marked[0][i]=true;
        marked[3][i]=true;
    }
    whiteKnights[0].targetPosition=make_pair(0,1);
    whiteKnights[1].targetPosition=make_pair(0,0);
    whiteKnights[2].targetPosition=make_pair(0,2);
    blackKnights[0].targetPosition=make_pair(3,1);
    blackKnights[1].targetPosition=make_pair(3,0);
    blackKnights[2].targetPosition=make_pair(3,2);


    // calling the recursive function
    Invade(whiteKnights,blackKnights);

    //printing out total number of moves
    cout << "total number of knight moves=" << cnt;

}


int main(){


    knight whiteKnights[3];
    knight blackKnights[3];
    solution(whiteKnights,blackKnights);

}


