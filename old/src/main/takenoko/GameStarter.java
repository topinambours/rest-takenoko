package takenoko;

import dnl.utils.text.table.TextTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import takenoko.util.Console;
import takenoko.util.exceptions.EmptyDeckException;
import takenoko.util.exceptions.NoActionSelectedException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@ImportResource("default-spring.xml")
@SpringBootApplication
public class GameStarter {

    private List<Game> games;

    private int nbGame;

    @Autowired
    public GameStarter(ApplicationContext appContext, ApplicationArguments appArgs) {
        Console.Log.init(appArgs.getOptionValues("consoleMode").get(0));
        this.nbGame = Integer.parseInt(appArgs.getOptionValues("n").get(0));

        games = new ArrayList<>();
        IntStream.range(0,nbGame).forEach(i -> games.add(appContext.getBean("standardGame", Game.class)));

        games.forEach(game1 -> {
            try {
                game1.play();
            } catch (EmptyDeckException e) {
                e.printStackTrace();
            } catch (NoActionSelectedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Smart vs Random");

        report();

        games = new ArrayList<>();
        IntStream.range(0,nbGame).forEach(i -> games.add(appContext.getBean("standardGame2", Game.class)));

        games.forEach(game1 -> {
            try {
                game1.play();
            } catch (EmptyDeckException e) {
                e.printStackTrace();
            } catch (NoActionSelectedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("====================\n\nSmart vs Smart vs Random");

        report();
    }


    public void report(){
        Game support = games.get(0);
        int sizeGame = games.size();

        String[] columnNames = new String[12];
        columnNames[0] = "Joueur";
        columnNames[1] = "Total Points";
        columnNames[2] = "Points/parties (moy)";
        columnNames[3] = "Objectifs";
        columnNames[4] = "Objectifs/(moy)";
        columnNames[5] = "Obj Panda";
        columnNames[6] = "Obj Pand(moy)";
        columnNames[7] = "Obj Pattern";
        columnNames[8] = "Obj Patt(moy)";
        columnNames[9] = "Obj Jardinier";
        columnNames[10] = "Obj Jard(moy)";
        columnNames[11] = "Empereur (%)";


        String[][] data = new String[support.getJoueurs().size()][];

        int totalPoint;
        int totalObjectif;
        float pandaObj;
        float gardenObj;
        float patternObj;
        List<Integer> empereur;
        empereur = new ArrayList<>();

        for (int i = 0; i < support.getJoueurs().size(); i++){
            totalPoint = 0;
            totalObjectif = 0;
            pandaObj  =0;
            gardenObj = 0;
            patternObj = 0;
            data[i] = new String[12];

            int emp = 0;

            data[i][0] = "Robot_" + support.getJoueurs().get(i).getId();

            for (Game game : games){
                totalPoint += game.getJoueurs().get(i).getScore();
                totalObjectif += game.getJoueurs().get(i).getObjectifComplete();
                pandaObj += game.getJoueurs().get(i).getObjPanda();
                patternObj += game.getJoueurs().get(i).getObjPattern();
                gardenObj += game.getJoueurs().get(i).getObjGarden();
                if(game.getEmpereur() == game.getJoueurs().get(i)){
                    emp++;
                }
            }
            empereur.add(emp);

            data[i][1] = "" + totalPoint;
            data[i][2] = "" + totalPoint / sizeGame;
            data[i][3] = "" + totalObjectif;
            data[i][4] = "" + totalObjectif / sizeGame;
            data[i][5] = "" + (int)pandaObj;
            data[i][6] = "" + pandaObj / sizeGame;
            data[i][7] = "" + (int)patternObj;
            data[i][8] = "" + patternObj / sizeGame;
            data[i][9] = "" + (int)gardenObj;
            data[i][10] = "" + gardenObj / sizeGame;
            data[i][11] = "" + (float) (empereur.get(i) * 1.0 / games.size() * 100.0);
        }


        TextTable tt = new TextTable(columnNames, data);
        tt.setSort(2, SortOrder.DESCENDING);
        tt.printTable();
    }

    public static void main(String[] args) {
        // On initialise la console avec le mode souhaité {release, debug, test}
        SpringApplication.run(GameStarter.class, args);
    }

}
