package kirpiks.erp;

public class Utils {

    public static String[] concatArrays(String[] arr1, String[] arr2) {
        String[] res = new String[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, res, 0, arr1.length);
        System.arraycopy(arr2, 0, res, arr1.length, arr2.length);
        return res;
    }

}
