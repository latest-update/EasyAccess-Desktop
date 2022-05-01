package App;

public class WindowNames {
    private static String [] names = {
        "EasyAccess",
        "EasyAccess - QR Refresh",
        "EasyAccess - Successfully recorded",
        "EasyAccess - Gate Simulator"
    };

    public static String windowName (String nameRoute)
    {
        switch (nameRoute)
        {
            case "default":
                nameRoute = names[0];
                break;
            case "refreshing":
                nameRoute = names[1];
                break;
            case "recorded":
                nameRoute = names[2];
                break;
            case "default-gate":
                nameRoute = names[3];
                break;
        }
        return nameRoute;
    }
}
