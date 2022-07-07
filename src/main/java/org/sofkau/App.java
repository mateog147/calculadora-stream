package org.sofkau;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Scanner scan = new Scanner(System.in);

    public static void main( String[] args )
    {
        Integer op = menu();
        if(op != 0){
            List<Double> listA = new ArrayList<>();
            List<Double> listB = new ArrayList<>();
            Boolean flag = Boolean.TRUE;
            //alimento las listas
            while(flag){
                System.out.println("Ingrese  numero arreglo 1  [s para salir]: ");
                String input = scan.nextLine();
                if(input.equals("s")){
                    flag = Boolean.FALSE;
                    break;
                }
                listA.add(Double.parseDouble(input));

                System.out.println("Ingrese  numero arreglo 2: ");
                String input_B = scan.nextLine();
                listB.add(Double.parseDouble(input_B));
            }
            seleccion(op,listA, listB);


        }
        System.out.println("Saliendo..");


    }

    private static void seleccion(Integer op, List<Double> listA, List<Double> listB) {
        switch (op){
            case 1 -> suma(listA, listB);
            case 2 -> resta(listA, listB);
            case 3 -> multiplicacion(listA, listB);
            case 4 -> division(listA, listB);
            case 5 -> modulo(listA, listB);
            case 6 -> sumaReduce(listA, listB);
            default -> System.out.println("error");
        }
    }




    public static void suma(List<Double> listA, List<Double> listB){
        BinaryOperator<Double> sum = (a , b) -> a + b;
        operar(listA, listB , sum);
    }

    private static void resta(List<Double> listA, List<Double> listB) {
        BinaryOperator<Double> res = (a , b) -> a - b;
        operar(listA, listB , res);
    }

    private static void multiplicacion(List<Double> listA, List<Double> listB) {
        BinaryOperator<Double> mult = (a , b) -> a * b;
        operar(listA, listB , mult);
    }

    private static void division(List<Double> listA, List<Double> listB) {
        List<Double> divisor = depurar(listB);
        BinaryOperator<Double> div = (a , b) -> a / b;

        operar(listA, divisor , div);
    }


    private static void modulo(List<Double> listA, List<Double> listB) {
        List<Double> divisor = depurar(listB);
        BinaryOperator<Double> mod = (a , b) -> a % b;

        operar(listA, divisor , mod);
    }

    private static void sumaReduce(List<Double> listA, List<Double> listB) {
        Optional<Double> prod1 = listA
                .stream()
                .reduce( (total, num) -> total*num);

        Optional<Double> prod2 = listB
                .stream()
                .reduce( (total, num) -> total*num);

        Double suma = prod1.get() + prod2.get();
        System.out.println("Resultado: " + suma);
    }

    private static void operar(List<Double> listA, List<Double> listB, BinaryOperator<Double> method) {
        AtomicInteger index = new AtomicInteger();
        List<Double> result =  listA.stream()
                .map((e)-> {
                    Double b =  listB.get(index.get());
                    index.getAndIncrement();
                    return method.apply(Double.valueOf(e) , Double.valueOf(b));
                })
                .collect(Collectors.toList());
        System.out.println("Arreglo 1:        "+listA.toString());
        System.out.println("Arreglo 2:        "+listB.toString());
        System.out.println("Arreglo resultado:"+result.toString());
    }

    private static List<Double> depurar(List<Double> listB) {
        return  listB.stream()
                .map(e -> {
                    if(e == 0.0){
                        System.out.println("No se puede dividir por cero, se remplazara por 1");
                        return 1.0;
                    }
                    return e;
                })
                .collect(Collectors.toList());
    }


    private static Integer menu() {
        List posiblesOpciones = Arrays.asList("1", "2","3",  "4", "5", "6","0");
        String opcion;
        System.out.println("--MENU--\n" +
                "1.Sumar\n" +
                "2.Restar\n" +
                "3.Multiplicar\n" +
                "4.Dividir\n" +
                "5.Modulo\n" +
                "6.Suma de los productos de dos arreglos\n" +
                "0.Salir");
        while (true){
            opcion = scan.nextLine();
            if(posiblesOpciones.contains(opcion)){
                return Integer.parseInt(opcion);
            }
            System.out.println("Opcion invalida");
        }
    }
}
