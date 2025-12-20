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

void part1(const vector<string>& input) {
    int res = 0;
    regex re(R"((\d+)x(\d+)x(\d+))");

    for (const auto& line : input) {
        smatch m;
        if (regex_search(line, m, re)) {
            int l = stoi(m[1].str());
            int w = stoi(m[2].str());
            int h = stoi(m[3].str());

            int area1 = l * w;
            int area2 = w * h;
            int area3 = h * l;
            int lineRes = 2 * (area1 + area2 + area3) + min({area1, area2, area3});
            res += lineRes;
        } else {
            cerr << "No match found for line: " << line << endl;
        }
    }

    cout << "Part 1: " << res << endl;
}

void part2(const vector<string>& input) {
    int res = 0;
    regex re(R"((\d+)x(\d+)x(\d+))");

    for (const auto& line : input) {
        smatch m;
        if (regex_search(line, m, re)) {
            int l = stoi(m[1].str());
            int w = stoi(m[2].str());
            int h = stoi(m[3].str());

            int dims[3] = {l, w, h};
            sort(dims, dims + size(dims));
            int lineRes = 2 * (dims[0] + dims[1]) + (l * w * h);
            res += lineRes;
        } else {
            cerr << "No match found for line: " << line << endl;
        }
    }

    cout << "Part 2: " << res << endl;
}

int main() {
    try {
        vector<string> input = getInput();
        cout << "Input : \n";
        for (const auto& line : input) cout << line << endl;
        cout << "\n" << endl;

        part1(input);
        part2(input);
    } catch (const exception& e) {
        cerr << "Failed to read input: " << e.what() << endl;
    }

    return 0;
}
