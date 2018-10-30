package takenoko;

import dnl.utils.text.table.TextTable;
import org.springframework.context.ApplicationContext;
import takenoko.util.Console;
import takenoko.util.exceptions.EmptyDeckException;
import takenoko.util.exceptions.NoActionSelectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@ImportResource("classpath*:resources/default-config.xml")
@Import(Game.class)
public class GameStarter {

    private List<Game> games;

    @Autowired
    public GameStarter(int nbGame ,ApplicationContext appContext) throws EmptyDeckException, NoActionSelectedException {

        if (nbGame > 1){
            Console.Log.init("test");
        }

        games = new ArrayList<>();

        IntStream.range(0,nbGame).forEach(i -> games.add(appContext.getBean("standardGame", Game.class)));

        int i = 0;

        games.stream().forEach(game1 -> {
            try {
                game1.play();
            } catch (EmptyDeckException e) {
                e.printStackTrace();
            } catch (NoActionSelectedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Bench Complete");
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
            data[i][11] = "" + (float)empereur.get(i) / 10;
        }


        TextTable tt = new TextTable(columnNames, data);
        tt.setSort(2, SortOrder.DESCENDING);
        tt.printTable();
    }

}
