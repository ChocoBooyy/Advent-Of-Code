#include <iostream>
#include <fstream>
#include <string>
#include <vector>

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


// return the lexicographically-largest k-digit subsequence of s (preserve order)
string maxKDigits(const string& line, size_t k) {
    size_t n = line.size();
    if (k >= n) return line;
    size_t to_remove = n - k;
    string st;
    st.reserve(n);
    for (char c : line) {
        while (!st.empty() && to_remove > 0 && st.back() < c) {
            st.pop_back();
            --to_remove;
        }
        st.push_back(c);
    }
    return st.substr(0, k);
}

long solve(int k, vector<string>& input) {
    long total = 0;
    for (const auto& line : input) {
        long joltage = stoll(maxKDigits(line, (size_t)k));
        total += joltage;
    }
    return total;
}

void part1(vector<string>& input) {
    long res = solve(2, input);
    cout << "Part 1: " << res << endl;
}

void part2(vector<string>& input) {
    long res = solve(12, input);
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
