package Lesson2;

class DimensionError extends Exception {}
class ContentError extends Exception {}

public class Lesson2 {

    public static void main(String[] args) {

        String testString = "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";
        String[][] stringArray;
        float result;

        try {
            stringArray = convertStringToArray(testString);
            result = calculateArray(stringArray);
        }
        catch (DimensionError e) {
            System.out.println("Размер массива не равен 4*4");
            e.printStackTrace();
            return;
        } catch (ContentError e) {
            System.out.println("В массиве присутствует нечисловое значение: \"" + e.getCause().getMessage() + "\"");
            e.printStackTrace();
            return;
        }

        System.out.printf("Половина от суммы элементов массива равна %.1f.", result);
    }

    private static String[][] convertStringToArray(String str)
            throws DimensionError {
        String[] tempArray;
        String[][] resultArray;
        tempArray = str.split("\n");
        if(tempArray.length != 4) {
            throw new DimensionError();
        }
        resultArray = new String[tempArray.length][];
        for (int i = 0; i < tempArray.length; i++) {
            resultArray[i] = tempArray[i].split(" ");
            if(resultArray[i].length != 4) {
                throw new DimensionError();
            }
        }
        return resultArray;
    }

    private static float calculateArray(String[][] array)
            throws ContentError {

        int result = 0;
        for (String[] row : array) {
            for (String value : row) {
                try {
                    result += Integer.parseInt(value.trim());
                } catch (NumberFormatException e) {
                    System.err.println("Элемент массива \"" + value + "\" не может быть преобразован в число.");
                    ContentError contentError = new ContentError();
                    contentError.initCause(new NumberFormatException(value));
                    throw contentError;
                }
            }
        }
        return ((float) result) / 2;
    }
}
