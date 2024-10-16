import java.util.Arrays;

public class List {
    private Object[] array;

    public void add(Object element){
        for( int i = 0; i< this.array.length;i++){

            if(this.array[i] == null){
                this.array[i] = element;
                return;
            }
        }
        Object [] arrayTemp = new Object[this.array.length+1];
        System.arraycopy(array, 0, arrayTemp, 0, this.array.length);
        arrayTemp[this.array.length] = element;
        this.array = arrayTemp;
    }

    public Object get(int position){
        if(position >= this.array.length){
            return null;
        }
        return this.array[position];
    }

    public boolean contains(Object element){
        for(Object o: this.array){
            if(element == o){
                return false;
            }
        }
        return true;
    }

    public void remove(Object element){
        int removeIndex = -1;
        for(int i = 0; i< this.array.length;i++){
            if(this.array[i].equals(element)){
                removeIndex = i;
                break;
            }
        }
        if(removeIndex > -1){
            for(int i = removeIndex;i < this.array.length;i++){
                if((i + 1) < this.array.length){
                    this.array[i] = this.array[i + 1];

                }
                if((i+1) == this.array.length){
                    Object [] arrayTemp = new Object[this.array.length-1];
                    System.arraycopy(array, 0, arrayTemp, 0, this.array.length -1);
                    this.array = arrayTemp;
                }
            }
        };
    }

    public int size(){
        return this.array.length;
    }

    public Object[] getList(){
        return this.array;
    }

    public List(){
        this.array = new Object[0];
    }

    public static void main(String[] args) {
        List l  = new List();
        System.out.println(Arrays.toString(l.getList()));
        l.add("test");
        l.add("test2");
        l.add("test3");
        l.add("test4");

        System.out.println(Arrays.toString(l.getList()));
        l.remove("test2");
        System.out.println(Arrays.toString(l.getList()));



    }
}
