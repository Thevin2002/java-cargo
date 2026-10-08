import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CargoTrackingSimulation extends JFrame {
    private JLabel timeLabel;
    private JPanel mapPanel, infoPanel;
    private Timer timer;
    private int car1X, car1Y, car2X, car2Y;
    private int car1Direction = 0;
    private int car2Direction = 0;
    private boolean car2Started = false;
    private JLabel car1DimensionLabel, car2DimensionLabel, car1TimeLabel, car2TimeLabel;
    private JLabel car1LatitudeLabel, car1LongitudeLabel, car2LatitudeLabel, car2LongitudeLabel;

    public CargoTrackingSimulation() {
        setTitle("Cargo Tracking Simulation");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        timeLabel = new JLabel("Time: ");

        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setColor(Color.WHITE);
                g.fillRect(0, 150, getWidth(), 100);

                g.setColor(Color.RED);
                g.fillRect(car1X, car1Y, 20, 10);

                if (car2Started) {
                    g.setColor(Color.BLUE);
                    g.fillRect(car2X, car2Y, 20, 10);
                }
            }
        };

        infoPanel = new JPanel(new GridLayout(5, 2));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel car1Label = new JLabel("Car 1:");
        car1Label.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel car2Label = new JLabel("Car 2:");
        car2Label.setHorizontalAlignment(SwingConstants.RIGHT);
        car1DimensionLabel = new JLabel();
        car2DimensionLabel = new JLabel();
        car1TimeLabel = new JLabel();
        car2TimeLabel = new JLabel();
        car1LatitudeLabel = new JLabel();
        car1LongitudeLabel = new JLabel();
        car2LatitudeLabel = new JLabel();
        car2LongitudeLabel = new JLabel();
        infoPanel.add(car1Label);
        infoPanel.add(car2Label);
        infoPanel.add(car1DimensionLabel);
        infoPanel.add(car2DimensionLabel);
        infoPanel.add(car1TimeLabel);
        infoPanel.add(car2TimeLabel);
        infoPanel.add(car1LatitudeLabel);
        infoPanel.add(car2LatitudeLabel);
        infoPanel.add(car1LongitudeLabel);
        infoPanel.add(car2LongitudeLabel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(timeLabel, BorderLayout.NORTH);
        panel.add(infoPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        add(mapPanel, BorderLayout.CENTER);

        // Start the timer to simulate movement and update coordinates
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update current time
                updateTime();

                // Move car1 in the specified direction
                switch (car1Direction) {
                    case 0: // Right
                        if (car1X < mapPanel.getWidth() - 20) {
                            car1X += 5;
                        } else if (car1Y >= 160 && car1Y <= 250) {
                            car1Direction = 1;
                        }
                        break;
                    case 1: // Down
                        if (car1Y < 250) {
                            car1Y += 5;
                        } else {
                            car1Direction = 2;
                        }
                        break;
                    case 2: // Left
                        if (car1X > 0) {
                            car1X -= 5;
                        } else {
                            car1Direction = 3;
                        }
                        break;
                    case 3: // Up
                        if (car1Y > 160) {
                            car1Y -= 5;
                        } else {
                            car1Direction = 0;
                        }
                        break;
                }

                // Move car2 if started
                if (car2Started) {
                    switch (car2Direction) {
                        case 0: // Right
                            if (car2X < mapPanel.getWidth() - 20) {
                                car2X += 5;
                            } else if (car2Y >= 160 && car2Y <= 250) {
                                car2Direction = 1;
                            }
                            break;
                        case 1: // Down
                            if (car2Y < 250) {
                                car2Y += 5;
                            } else {
                                car2Direction = 2;
                            }
                            break;
                        case 2: // Left
                            if (car2X > 0) {
                                car2X -= 5;
                            } else {
                                car2Direction = 3;
                            }
                            break;
                        case 3: // Up
                            if (car2Y > 160) {
                                car2Y -= 5;
                            } else {
                                car2Direction = 0;
                            }
                            break;
                    }
                } else {
                    // Delay car2 start by 5 seconds (5000 milliseconds)
                    if (timer.getDelay() * timer.getInitialDelay() >= 10000 && !car2Started) {
                        car2Started = true;
                        car2X = 0;
                        car2Y = 160;
                        car2Direction = 0;
                    }
                }

                // Update GUI with new coordinates and time
                car1DimensionLabel.setText("Dimensions: 20x10");
                car2DimensionLabel.setText("Dimensions: 20x10");
                car1TimeLabel.setText("Time: " + timeLabel.getText().substring(6));
                car2TimeLabel.setText("Time: " + timeLabel.getText().substring(6));
                car1LatitudeLabel.setText("Latitude: " + latitude(car1Y));
                car1LongitudeLabel.setText("Longitude: " + longitude(car1X));
                car2LatitudeLabel.setText("Latitude: " + latitude(car2Y));
                car2LongitudeLabel.setText("Longitude: " + longitude(car2X));


                mapPanel.repaint();
            }
        });


        car1X = 0;
        car1Y = 160;


        timer.start();
    }

    // Update current time
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        timeLabel.setText("Time: " + currentTime);
    }


    private double latitude(int y) {
        return ((double) y / mapPanel.getHeight()) * 180 - 90;
    }


    private double longitude(int x) {
        return ((double) x / mapPanel.getWidth()) * 360 - 180;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CargoTrackingSimulation().setVisible(true);
            }
        });
    }

}

