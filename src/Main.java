//import com.google.gdata.data.TextConstruct;
//import com.google.gdata.data.spreadsheet.CellEntry;
//import com.google.gdata.data.spreadsheet.ListEntry;
//import com.google.gdata.util.ServiceException;
//import org.apache.poi.ss.usermodel.*;
import org.epochx.life.GenerationAdapter;
import org.epochx.life.Life;
import org.epochx.stats.StatField;
import org.epochx.stats.Stats;

import javax.xml.soap.Text;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Main {
    public static ZMath math = new ZMath();
    private List<List<Creature>> groups = new ArrayList<>();
    private List<Creature> thieves = new ArrayList<>();
    double foodOfGiver = 0;
    double foodOfMeany = 0;
    double timesAsk = 0;
    double timesNeedFood = 0;
    double timesGive = 0;




    public Main() throws Exception {

        final Altruism altruism = new Altruism();
        Sheets sheets = null;//Sheets.get();

        Life.get().addGenerationListener(new GenerationAdapter() {
            @Override
            public void onGenerationStart() {
                System.out.println("gen: " + Stats.get().getStat(StatField.GEN_NUMBER));

                for (List<Creature> group : groups) {
                    for (Creature creature : group) {
                        creature.setFood(math.startFood);
                    }
                }

                int day;
                for(day = 1; day < math.getLifeSpan(); day++) {


                    for(List<Creature> group : groups) {
                        if(day == 1) System.out.println("groups; " + group.size());
                        for(int groupIndex = 0; groupIndex < group.size(); groupIndex++) {
                            Creature creature = group.get(groupIndex);

                            creature.setFood(creature.getFood() + math.deltaFood());

                            Creature victim = group.get(new Random().nextInt(group.size()));
                            altruism.getCuzRequired().setValue(-1.0);
                            altruism.getFood().setValue(creature.getFood());
                            if(creature.getFriends().contains(victim)) {
                                altruism.getIsFriend().setValue(1.0);
                            } else {
                                altruism.getIsFriend().setValue(10.0);
                            }

                            double relation = relation(victim, creature);
                            altruism.getRelation().setValue(relation);
                            altruism.getFoodHeHas().setValue(victim.getFood());
                            altruism.getBystanders().setValue(group.size());
                            if(creature.getEnemies().contains(victim)) {
                                altruism.getIsEnemy().setValue(10.0);
                            } else {
                                altruism.getIsEnemy().setValue(1.0);
                            }
                            if(thieves.contains(victim)) {
                                altruism.getIsThief().setValue(10.0);
                            } else {
                                altruism.getIsThief().setValue(1.0);
                            }
                            double foodTaken = (Double) creature.evaluate();

                            if(foodTaken > 0 && foodTaken < victim.getFood()) {
                                creature.setFood(creature.getFood() + foodTaken);
                                victim.setFood(victim.getFood() - foodTaken);
                                thieves.add(creature);
                                victim.getEnemies().add(creature);
                            }

                            timesAsk++;


                            altruism.getBystanders().setValue(group.size());

                            if(creature.getFood() <= 0) {
                                timesNeedFood++;
                                if(false && groups.indexOf(group) == 0 && group.contains(creature) && group.indexOf(creature) < 5) {
                                    //ListEntry row = sheets.getRows().get(group.indexOf(creature));
                                    //row.getCustomElements().setValueLocal("foods", String.valueOf(.1));

                                    try {
                                        //row.setEtag("*");
                                        //row.update();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                for(int i = 0; i < group.size(); i++) {
                                    Creature friend = group.get(i);

                                    if(thieves.contains(creature)) {
                                        altruism.getIsThief().setValue(10.0);
                                    } else {
                                        altruism.getIsThief().setValue(1.0);
                                    }
                                    if(friend.getEnemies().contains(creature)) {
                                        altruism.getIsEnemy().setValue(10.0);
                                    } else {
                                        altruism.getIsEnemy().setValue(1.0);
                                    }
                                    altruism.getCuzRequired().setValue(1.0);
                                    altruism.getFood().setValue(group.get(i).getFood());
                                    if(friend.getFriends().contains(creature)) {
                                        altruism.getIsFriend().setValue(1.0);
                                    } else {
                                        altruism.getIsFriend().setValue(10.0);
                                    }
                                    double relationToVictim = relation(friend, creature);
                                    altruism.getRelation().setValue(relationToVictim);
                                    altruism.getFoodHeHas().setValue(creature.getFood());

                                    timesAsk++;


                                    altruism.getBystanders().setValue(group.size());

                                    Double foodGiven = (Double) friend.evaluate();


                                    if(foodGiven > 0 && foodGiven < friend.getFood()) {

                                        timesGive++;
                                        foodOfGiver += friend.getFood();

                                        friend.setFood(friend.getFood() - foodGiven);
                                        creature.setFood(foodGiven);
                                        creature.getFriends().add(friend);

                                        if(false && groups.indexOf(group) == 0 && group.contains(creature) && group.indexOf(creature) < 5) {
                                            //CellEntry cell = sheets.getCell("C2").get();
                                            //cell.changeInputValueLocal(String.valueOf(group.indexOf(creature) + 1));
                                            try {
                                                //cell.update();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        break;
                                    } else foodOfMeany += friend.getFood();
                                }
                                if(creature.getFood() <= 0) {
                                    if(false && groups.indexOf(group) == 0 && group.contains(creature) && group.indexOf(creature) < 5) {
                                        //ListEntry row = sheets.getRows().get(group.indexOf(creature));
                                        //row.getCustomElements().setValueLocal("foods", "0");

                                        try {
                                            //row.setEtag("*");
                                            //row.update();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    group.remove(creature);
                                    creature.setFitness(math.getLifeSpan() - day + 2);
                                }
                            }

                            if(creature.getFood() > math.foodLimit) creature.setFood(math.foodLimit);

                            if(false && groups.indexOf(group) == 0 && group.contains(creature) && group.indexOf(creature) < 5) {
                                //ListEntry row = sheets.getRows().get(group.indexOf(creature));
                                //row.getCustomElements().setValueLocal("foods", Double.valueOf(creature.getFood()).toString());

                                try {
                                    //row.setEtag("*");
                                    //row.update();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }


                for(List<Creature> group : groups) {
                    for(Creature survivor : group) {
                        survivor.setFitness(1);
                    }
                    group.clear();
                }

            }

        });


        Life.get().addHook(new AltruismHook(math, groups));

        altruism.run();

        System.out.println("give rate: " + timesGive/timesAsk);
        System.out.println("saved rate: " + timesGive/timesNeedFood);
        System.out.println("brother : " + ones/stuffs);
        System.out.println("foodOfGiver: " + foodOfGiver/timesGive);
        System.out.println("foodOfMeanie: " + foodOfMeany/(timesAsk - timesGive));

        new Experiment(altruism);
    }


    public static void main(String[] args) throws Exception {
        new Main();
    }

    double ones = 0;
    double stuffs = 0;
    public Double relation(Creature c1, Creature c2) {
        stuffs++;
        List<Creature> creatures = Arrays.asList(c1, c2);
        List<Creature> parents = new ArrayList<>();
        List<Creature> grandParents = new ArrayList<>();

        for(Creature creature : creatures) {

            for(Creature parent : creature.getParents()) {
                if(parents.contains(parent)) {
                    ones++;
                    return 1.0;
                }

                parents.add(parent);



            }
        }

        for(Creature creature : creatures) {
            for(Creature parent : creature.getParents()) {
                for(Creature grandParent : parent.getParents()) {
                    if (grandParents.contains(grandParent)) {
                        return 2.0;
                    } else
                        grandParents.add(grandParent);
                }
            }
        }

        return 10.0;
    }


    public void excel() {
        try {
            //InputStream inp = new FileInputStream("/Users/Joseph/Documents/firstJavaExcel.xlsx");
            //Workbook wb = WorkbookFactory.create(new FileInputStream("/Users/Joseph/Documents/firstJavaExcel.xlsx"));
            //Sheet sheet = wb.getSheetAt(0);
            //System.out.println(sheet.getSheetName());
            //Row row = sheet.getRow(0);
            //if(row == null) {
                //row = sheet.createRow(0);
              //  System.out.println("row: " + row);
            //}
            //Cell cell = row.getCell(0);
//            if (cell == null) {
//                cell = row.createCell(0);
//            }
            //System.out.printf("cell: " + cell.getStringCellValue());
            //cell.setCellType(Cell.CELL_TYPE_STRING);
            //cell.setCellValue("a test");
            //System.out.println(sheet.createRow(2).createCell(0).getStringCellValue());


            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream("/Users/Joseph/Documents/firstJavaExcel.xlsx");

            //wb.write(fileOut);

            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}