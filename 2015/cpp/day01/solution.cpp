#include <iostream>
#include <fstream>
#include <string>
using namespace std;

string getInput() {
    ifstream file("input.txt");
    string input, line;

    while (getline(file, line)) {
        input += line + "\n";
    }

    file.close();
    return input;
}

void part1(string input) {
    int res = 0;
    for (int i = 0; i < input.length(); i++) {
        if (input[i] == '(') {
            res++;
        } else if (input[i] == ')') {
            res--;
        }
    }

    cout << "Part1: " << res << endl;
}

void part2(string input) {
    int res = 0, i = 0;
    while (i < input.length() && res >= 0) {
        if (input[i] == '(') {
            res++;
        } else if (input[i] == ')') {
            res--;
        }
        i++;
    }
    
    cout << "Part2: " << i << endl;
}

int main() {
    string input = getInput();
    cout << "Input : \n" << input << endl;

    part1(input);
    part2(input);
    return 0;
}
