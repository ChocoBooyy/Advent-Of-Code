#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <utility>
#include <algorithm>
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
    int ns = 0, ew = 0;
    vector<pair<int, int>> houses = {make_pair(0, 0)};
    for (int i = 0; i < input.length(); i++) {
        switch (input[i])
        {
        case '^':
            ns++;
            break;
        case 'v':
            ns--;
            break;
        case '>':
            ew++;
            break;
        case '<':
            ew--;
            break;
        default:
            break;
        }

        auto tmp = find(houses.begin(), houses.end(), make_pair(ns, ew));
        if (tmp == houses.end()) {
            houses.push_back({ns, ew});
        }
    }

    cout << "Part1: " << houses.size() << endl;
}

void part2(string input) {
    int ns, ew, rns, rew;
    ns = ew = rns = rew = 0;
    vector<pair<int, int>> houses = {make_pair(0, 0)};
    vector<pair<int, int>> rhouses = {make_pair(0, 0)}; // Need to do -1 on the result calculation
    for (int i = 0; i < input.length(); i++) {
        bool robot = (i % 2 != 0);

        if (robot) {
            switch (input[i])
            {
            case '^':
                rns++;
                break;
            case 'v':
                rns--;
                break;
            case '>':
                rew++;
                break;
            case '<':
                rew--;
                break;
            default:
                break;
            }


            auto tmp = find(houses.begin(), houses.end(), make_pair(ns, ew));
            if (tmp == houses.end()) {
                houses.push_back({ns, ew});
            }
        } else {
            switch (input[i])
            {
            case '^':
                ns++;
                break;
            case 'v':
                ns--;
                break;
            case '>':
                ew++;
                break;
            case '<':
                ew--;
                break;
            default:
                break;
            }


            auto tmp = find(rhouses.begin(), rhouses.end(), make_pair(rns, rew));
            if (tmp == rhouses.end()) {
                rhouses.push_back({rns, rew});
            }
        }
    }

    int res = houses.size() + rhouses.size() - 1;
    cout << "Part1: " << res << endl;
}

int main() {
    string input = getInput();
    cout << "Input : \n" << input << endl;

    part1(input);
    part2(input);
    return 0;
}
