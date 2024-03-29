import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //displaty our weaher app gui
                new WeatherAppGui().setVisible(true);
               // System.out.println(WeatherApp.getlocationData("tokyo"));

                System.out.println(WeatherApp.getCurrentTime());
            }
        });
    }
}
