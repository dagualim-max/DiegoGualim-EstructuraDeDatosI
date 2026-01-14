#include <iostream>

using namespace std;

int main()
{
	int array[10], arrayPar[10], arrayImpar[10], contPar = 0, contImpar = 0;

	cout << "Ejercicio 1: " << endl;
	cout << "Ingrese 10 numeros enteros: " << endl;

	for (int i = 0; i < 10;i++)
	{
		cin >> array[i];
	}

	for (int i = 0; i < 10;i++)
	{
		if (array[i] % 2 == 0)
		{
			arrayPar[contPar] = array[i];

			contPar++;
		}
		else
		{
			arrayImpar[contImpar] = array[i];
			contImpar++;
		}
	}


	cout << "Arreglo de numeros pares: " << endl;

	for (int i = 0;i < contPar;i++)
	{
		cout << arrayPar[i] << endl;
	}

	cout << "\nArreglo de numeros impares: " << endl;

	for (int i = 0;i < contPar;i++)
	{
		cout << arrayImpar[i] << endl;
	}


	cout << "\nEjercicio 2: " << endl;

	char array1[5], array2[5], arrayCombinado[10];
	int cont1 = 0, cont2 = 0;

	cout << "Ingrese 5 caracteres para el arreglo 1: " << endl;

	for (int i = 0; i < 5;i++)
	{
		cin >> array1[i];
	}

	cout << "Ingrese 5 caracteres para el arreglo 1: " << endl;

	for (int i = 0; i < 5;i++)
	{
		cin >> array2[i];
	}

	for (int i = 0;i < 10;i++)
	{
		if (i % 2 == 0)
		{
			arrayCombinado[i] = array1[cont1];
			cont1++;
		}
		else
		{
			arrayCombinado[i] = array2[cont2];
			cont2++;
		}
	}

	for (int i = 0; i < 10;i++)
	{
		cout << arrayCombinado[i] << endl;
	}
}