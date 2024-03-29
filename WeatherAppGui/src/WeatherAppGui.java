import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {

    private JSONObject weatherData;
    public WeatherAppGui(){
        // setup our Gui and add a title
        super("Weather App");

        //configure gui to end the program once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set the size of pur gui(IN pixels)
        setSize(600,600);

        //load our gui to the center of the screen

        setLocationRelativeTo(null);
        //make our layout manager null to manually position our components within the gui
        setLayout(null);
        //prevent any resize of our GUI
        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents(){
        // search field
        JTextField searchTextField = new JTextField();

        //set the location and size of our component
        searchTextField.setBounds(15,15,351,45);
        //change the font style and size
        searchTextField.setFont(new Font("Dialog",Font.PLAIN,24));

        add(searchTextField);



        // weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/weatherapp_images/cloudy.png"));
        weatherConditionImage.setBounds(60,125,450,217);
        add(weatherConditionImage);

        // temperature text
        JLabel tempertureText = new JLabel("10 C");
        tempertureText.setBounds(60,350,450,54);
        tempertureText.setFont(new Font("Dialog",Font.BOLD,48));
        tempertureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(tempertureText);

        //weather condition description
        JLabel weatherConditionDesc = new JLabel("cloudy");
        weatherConditionDesc.setBounds(60,405,450,36);
        weatherConditionDesc.setFont(new Font("Dialog",Font.PLAIN,32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        //humidity image

        JLabel humidityImage = new JLabel(loadImage("src/assets/weatherapp_images/humidity.png"));
        humidityImage.setBounds(15,500,74,66);
        add(humidityImage);

        //humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity<b> 100%</html>");
        humidityText.setBounds(90,500,95,55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        //Windspeed image

        JLabel windSpeedImage = new JLabel(loadImage("src/assets/weatherapp_images/windspeed.png"));
        windSpeedImage.setBounds(220,500,70,66);
        add(windSpeedImage);

        //windSpeedText
        JLabel windSpeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windSpeedText.setBounds(310,500,100,55);
        windSpeedText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(windSpeedText);

        //search Button
        JButton searchButton = new JButton(loadImage("src/assets/weatherapp_images/search.png"));

        //change the cursor to a hand cursor when hovering over this button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,47,45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();

                //validate input and remove whitespaces
                if(userInput.replaceAll("\\s", "").length() <=0){
                    return;
                }
                // retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                //update gui

                //update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                switch(weatherCondition){
                    case "Clear" :
                        weatherConditionImage.setIcon(loadImage("src/assets/weatherapp_images/clear.png"));
                        break;
                    case "Cloudy" :
                        weatherConditionImage.setIcon(loadImage("src/assets/weatherapp_images/cloudy.png"));
                        break;
                    case "rain" :
                        weatherConditionImage.setIcon(loadImage("src/assets/weatherapp_images/rain.png"));
                        break;
                    case "snow" :
                        weatherConditionImage.setIcon(loadImage("src/assets/weatherapp_images/snow.png"));
                        break;
                }

                //update temperatrure text

                double temperature = (double) weatherData.get("temperature");
                tempertureText.setText(temperature + "C");

                //update weather condition

                weatherConditionDesc.setText(weatherCondition);

                //update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b>"+ humidity+"%</html>");

                //update windspeed text

                double windSpeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>Windspeed</b> "+ windSpeed+"km/h</html>");

            }
        });
        add(searchButton);
    }


    //used to create images in our gui components
    private ImageIcon loadImage(String resourcepath){
       try{
           //read the image file from the file path given
           BufferedImage image = ImageIO.read(new File(resourcepath));

           //returns an image icon so that our component canr ender it
           return new ImageIcon(image);
       }catch(IOException e){
           e.printStackTrace();
       }
       System.out.println("Could not find resource");
       return null;
    }
}