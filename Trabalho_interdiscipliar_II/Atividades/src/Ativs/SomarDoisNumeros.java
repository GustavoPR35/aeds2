package Ativs;
import java.util.Scanner;

public class SomarDoisNumeros {
	
	public static int somarNumeros(int x, int y) {
		return x + y;
	}

	
	public static void main(String[] args) {
		//scanner para entrada
		Scanner sc = new Scanner(System.in);
		
		//declaração de variáveis
		int x, y, soma;
		
		//atribuindo valores às variáveis por entrada do usuário
		x = sc.nextInt();
		y = sc.nextInt();
		
		//somando os valores
		soma = somarNumeros(x, y);
		
		//mostrar o resultado final
		System.out.println(soma);
		
		sc.close();

	}

}
