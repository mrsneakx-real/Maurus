package maurus;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;
import maurus.features.bluescreen.FakeBlueScreen;
import maurus.features.popups.alainberset.Alain;
import maurus.features.popups.error.Error;
import maurus.features.lagmachine.Heavylag;
import maurus.features.lagmachine.Lightlag;
import maurus.features.openapp.Openapp;
import maurus.features.popups.bounce.Donald;
import maurus.features.popups.bounce.Joe;
import maurus.features.popups.bounce.Dvd;
import maurus.features.ragebait.Ragebait;
import maurus.features.ragebait.SecondRagebait;
import maurus.features.solvecode.Todo;
import maurus.features.popups.defender.ToastFromJava;
import maurus.features.popups.ad.Ad;


import java.awt.*;
import java.io.IOException;

public class App extends Application {

    int gulasch;
    int cmax = 14; //Number of cases
    int cmin = 1; //Has to be 1
    boolean waiter = false;
    private static double Appclosetime = 1;//minutes

    @Override
    public void start (Stage primaryStage) throws Exception {

        System.out.println("Maurus started on --debug --version 1.0.4");

        Platform.setImplicitExit(false); // keep the application alive
        waiter = true;

        //new ProcessBuilder("C:\\Program Files (x86)\\Maurus\\checkmaurus.exe").start();

        openRandomClass();

    }
    private void openRandomClass() throws Exception {
        Timeline timeline = null;
        if (waiter) {
            waiter = false;
            int minwait = 30;//seconds
            int maxwait = 60;//seconds
            int timewait = minwait + (int)(Math.random() * (maxwait - minwait));
            System.out.println("Time waited: " + timewait);
            timeline = new Timeline(new KeyFrame(Duration.seconds(timewait), event -> {

             //int gulasch = (int)(Math.random() * cmax - cmin + 1) + cmin; //Random number between cmax and cmin (inclusive cmax and cmin)
             int gulasch = 14;

                Stage stage = new Stage();
                // Coses based on the random number the file it wants to activate
                // all clases need a primarystage.close or something like that so that it works, maybe also possible in App
                switch (gulasch) {
                    case 1:
                        System.out.println(gulasch + "Bluescreen");
                        try {
                            new FakeBlueScreen().start(stage, () ->{
                                try {
                                    waiter = true;
                                    openRandomClass();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        System.out.println(gulasch + "Dvd");
                        new Dvd().start(stage, () ->{
                            System.out.println("dvd finished opening random shit");
                            try {
                                waiter = true;
                                openRandomClass();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    case 3:
                        System.out.println(gulasch + "Joe");
                        //alternative because there is no primary stage
                        new Joe().start(stage, () -> {
                            System.out.println("Joe finished, opening next feature...");
                            try {
                                waiter = true;
                                openRandomClass();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                        break;
                    case 4:
                        System.out.println(gulasch + "Donald");
                        new Donald().start(stage, () -> {
                            System.out.println("Donald finished, opening next feature...");
                            try {
                                waiter = true;
                                openRandomClass();
                            }
                            catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                        break;
                    case 5:
                        System.out.println(gulasch + "Todo");
                        try {
                            new Todo().start(stage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 6:
                        System.out.println(gulasch + "Ragebait (exit with K");

                        try {
                            new Ragebait().start(stage);
                        } catch (AWTException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 7:
                        System.out.println(gulasch + "Toast");
                        try {
                            int randomCase = (int) (Math.random() * 3) + 1;
                            new ToastFromJava(randomCase).start(() ->{
                                System.out.println("Toast finished");
                                try {
                                    waiter = true;
                                    openRandomClass();
                                }catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case 8:
                        System.out.println(gulasch + "secondragebait (Blockinput)");
                        new SecondRagebait().start(stage, () -> {
                            System.out.println("secondRagebait finished, opening next feature...");
                            try {
                                waiter = true;
                                openRandomClass();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                        break;
                    case 9:
                        System.out.println(gulasch + "Heavylag");
                        new Heavylag().start(stage, () -> {
                            waiter = true;
                            try {
                                openRandomClass();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    case 10:
                        System.out.println(gulasch + "lightlag");
                        new Lightlag().start(stage, () -> {
                            waiter = true;
                            try {
                                openRandomClass();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    case 11:
                        System.out.println(gulasch + "waiter");
                        try {
                            new Openapp().start(stage, () -> {
                                waiter =  true;
                                try {
                                    openRandomClass();
                                }
                                catch (Exception ex) {
                                    throw new RuntimeException(ex);
                                }
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 12:
                        System.out.println(gulasch + "Error");
                        new Error().start(stage, () ->{
                            waiter = true;
                            try {
                                openRandomClass();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    case 13:
                        System.out.println(gulasch + "Ad");
                        try {
                            new Ad().start(stage, () -> {
                                waiter = true;
                                try {
                                    openRandomClass();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }

                            });
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 14:
                        System.out.println(gulasch + "Alain");
                        new Alain().start(stage, () -> {
                            waiter = true;
                            try {
                                openRandomClass();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    default:
                        System.out.println("Invalid Number c");
                        break;
                }
                //as soon as window/stage is closed
                stage.setOnHidden((e -> {
                    System.out.println("Feature closed, opening next one...");
                    // Open next feature
                    try {
                        waiter = true;
                        openRandomClass();

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }));
            }));
        }
        timeline.setCycleCount(1);//ensures it's only called once
        timeline.play();
    }
    public static double getAppclosetime(){
        return Appclosetime;
    }
}