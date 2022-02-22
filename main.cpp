#include <iostream>
#include <cmath>
#include <cstdlib>
#include <ctime>

using namespace std;


int main()
{
    //1
    //bool isA = (90 <= grade <= 100);
    //=> bool isA = (90 <= grade && grade <= 100);

    //2
    //double x = (double) (3/5);
	//cout << x; (cout ra 0)
	// => double x = double (3) / double (5); (ep kieu ve double trc khi chia)

    //3
    //int x = 65536;
	//long y = x * x;
	//cout << y; (in ra 0 vi qua gioi han chua cua int => fix x & y thanh long long)

	//4
	//cout << (sqrt(2) * sqrt(2) == 2) (ra = 0 vi (sqrt(2)*sqrt(2)) bi lam tron nen se != 2)

	//5
	// 2/0 (k in ra gi)
	//2.00 / 0.00 (ra inf)
	//2%0 (k in ra gi)

	//6
	//int a = 27 * "three"
	//(invalid operands of types 'int' and 'const char [6]' to binary 'operator*'|)
    //double x;
	//cout << x;
	//khi khai bao trong main in ra 7.90505e-323
	// khai bao x ngoai main in ra 0

	//7
	//int threeInt = 3;
	//int fourInt  = 4;
	//double threeDouble = 3.0;
	//double fourDouble  = 4.0;
	//cout << threeInt / fourInt << endl; (0)
	//cout << threeInt / fourDouble << endl; (0.75)
	//cout << threeDouble / fourInt << endl; (0.75)
	//cout << threeDouble / fourDouble << endl; (0.75)
	// => co 1 bien dang double se ep kqua ve double

	//8
	//int  arg1;
	//arg1 = -1;
	//int x, y, z;
	//char myDouble = '5';
	//char arg1 = 'A';
	//cout << arg1 << "\n";
	//conflicting declaration 'char arg1'|

	//9
	//int arg1;
	//arg1 = -1;
	//{
    //  char arg1 = 'A';
    //  cout << arg1 << "\n";
	//}
	//cout ra A vi cau lenh cout nam trong block lenh khai bao arg1 = 'A'

	//10
	//int arg1;
	//arg1 = -1;
	//{
    //  char arg1 = ’A’;
	//}
	//cout << arg1 << "\n"; (ra -1 vi cout nam ngoai block lenh)

	//11
	//double C = (F - 32) * (5 / 9);
	//sai do chua ep kieu (5/9) ve double nen kqua = 0

	//12
	//if (10 > 5);
	 //else; {
	    //cout << "Here";}; (in ra Here)

    //13
    //int  x, y;
    //cin >> x >> y;
    //cout << sqrt(x*x + y*y);

    //Hello GITHUB !!!!!


    return 0;
}
