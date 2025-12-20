#include <iostream>
#include <fstream>
#include <string>
#include <regex>
#include <charconv>

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

bool invalidId(const long& id) {
    string digits = to_string(id);
    int len = digits.size();
    if (len < 2 || (len % 2) != 0) return false; // must be at least two chars and even length

    int half = len / 2;
    // compare first half with second half without allocating substrings
    return digits.compare(0, half, digits, half, half) == 0;
}

bool invalidId2(const long& id) {
    string id_str = to_string(id);
    int len = id_str.size();
    if (len < 2) return false;   // cannot repeat if only 1 digit

    string doubled = id_str + id_str;

    // Look for id_str inside doubled, but not at the trivial positions
    size_t pos = doubled.find(id_str, 1);

    return pos != string::npos && pos < len;
}

void solve(const string& input) {
    long res = 0, res2 = 0;

    regex re("([0-9]+)-([0-9]+)");
    for (sregex_iterator it(input.begin(), input.end(), re), end; it != end; ++it)
    {
        smatch m = *it;

        string_view a(&*m[1].first, m[1].length());
        string_view b(&*m[2].first, m[2].length());

        long num = 0, num2 = 0;
        from_chars(a.data(), a.data() + a.size(), num);
        from_chars(b.data(), b.data() + b.size(), num2);

        for (long i = num-1; i < num2+1; ++i){
            if(invalidId(i)) // part 1
                res+=i;
            if(invalidId2(i)) // part 2
                res2+=i;
        }
    }

    cout << "Part 1: " << res << endl;
    cout << "Part 2: " << res2 << endl;
}

int main() {
    try {
        string input = getInput();
        cout << "Input : \n";
        cout << input << endl;
        cout << "\n" << endl;

        solve(input);
    } catch (const exception& e) {
        cerr << "Failed to read input: " << e.what() << endl;
    }

    return 0;
}
