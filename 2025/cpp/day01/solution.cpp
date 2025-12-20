#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <regex>
#include <algorithm>

using namespace std;

vector<string> getInput() {
    ifstream file("input.txt");
    vector<string> lines;
    string line;
    while (getline(file, line)) {
        if (!line.empty()) lines.push_back(line);
    }
    return lines;
}

void solve(vector<string>& input) {
    int res = 0, res2 = 0, dial = 50;

    regex re("([A-Za-z])([0-9]+)"); 
    for(const auto& line : input){
        smatch m;
        if(regex_match(line, m, re)) {
            char direction = m[1].str()[0];
            int num = stoi(m[2].str());

            int full_cycles = num / 100;
            int rest = num % 100;
            int start = dial;

            int steps_until_first_zero;
            if (direction == 'R') steps_until_first_zero = (100 - start) % 100;
            else steps_until_first_zero = start % 100;
            if (steps_until_first_zero == 0) steps_until_first_zero = 100;

            res2 += full_cycles + (rest >= steps_until_first_zero ? 1 : 0);

            num = rest;

            if(direction == 'L'){
                dial -= num;
                if(dial < 0) {
                    dial = 100 + dial;
                }
            } else if(direction == 'R'){
                dial += num;
                if(dial > 99) {
                    dial = dial - 100;
                }
            }

            if(dial == 0) {
                res++;
            }
        } else {
            cerr << "No match found for line: " << line << endl;
        }
    }

    cout << "Part 1: " << res << endl;
    cout << "Part 2: " << res2 << endl;
}

int main() {
    try {
        vector<string> input = getInput();
        cout << "Input : \n";
        for (const auto& line : input) cout << line << endl;
        cout << "\n" << endl;

        solve(input);
    } catch (const exception& e) {
        cerr << "Failed to read input: " << e.what() << endl;
    }

    return 0;
}
