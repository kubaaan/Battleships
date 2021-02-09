package game;

public class Grid {

    public static final int SIZE = 10;

    private String[][] oceanMap;

    public Grid() {
        oceanMap = new String[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                oceanMap[x][y] = " ";
            }
        }
    }

    //revealed ships
    public void printPlayersMap(){
        System.out.println("   0123456789   ");
        for(int x = 0; x < oceanMap.length; x++){
            System.out.print(x + " |");
            for(int y = 0; y < oceanMap[x].length; y++) {
                //System.out.print(x);
                System.out.print(oceanMap[x][y]);
            }
            System.out.println("| " + x);
        }
        System.out.println("   0123456789   ");
    }

    public void printPCMap(){
        System.out.println("   0123456789   ");
        for(int x = 0; x < oceanMap.length; x++){
            System.out.print(x + " |");
            for(int y = 0; y < oceanMap[x].length; y++) {
                switch (oceanMap[x][y]) {
                    //ship hit
                    case "1":
                        System.out.print("X");
                        break;
                        //missed
                    case "2":
                        System.out.print("O");
                        break;
                    default:
                        //field unrevealed
                        System.out.print(" ");
                        break;
                }
            }
            System.out.println("| " + x);
        }
        System.out.println("   0123456789   ");
    }

    public boolean validateShipsDeployment(int axisX, int axisY, char direction, int shipsLength){
        if(direction=='v'){
            int lengthOnMap = (axisY + shipsLength)-1;
            if(lengthOnMap > Grid.SIZE){
                System.out.println("Ship out of map");
                return false;
            }
            for(int y = axisY; y <= lengthOnMap; y++){
                if(!oceanMap[axisX][y].equals(" ")){
                    System.out.println("Ships collision!");
                    return false;
                }
            }
        }else if(direction=='h'){
            int lengthOnMap = (axisX + shipsLength)-1;
            if(lengthOnMap > Grid.SIZE){
                System.out.println("Ships collision!");
                return false;
            }
            for(int x = axisX; x <= lengthOnMap; x++){
                if(!oceanMap[x][axisY].equals(" ")){
                    return false;
                }
            }
        }
        return true;
    }

    public void placeShipOnMap(int axisX, int axisY, char direction, int shipsLength){
        if(direction=='v'){
            int lengthOnMap = (axisY + shipsLength)-1;
            for(int y = axisY; y <= lengthOnMap; y++){
                oceanMap[axisX][y] = "@";
            }
        }else if(direction=='h'){
            int lengthOnMap = (axisX + shipsLength)-1;
            for(int x = axisX; x <= lengthOnMap; x++){
                oceanMap[x][axisY] = "@";
            }
        }
    }

    public int fieldStatus(int x, int y){
        String coordinates = oceanMap[x][y];
        if(coordinates.equals(" ")){
            return 2;//missed
        }else if(coordinates.equals("@")){
            return 1;//ship hit
        }else{
            return -1;//field revealed
        }
    }
    public void markHit(int x, int y){
        oceanMap[x][y] = "1";
    }
    public void markMissed(int x, int y){
        oceanMap[x][y] = "2";
    }
}
