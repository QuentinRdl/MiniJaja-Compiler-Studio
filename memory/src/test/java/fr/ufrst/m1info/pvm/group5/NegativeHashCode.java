package fr.ufrst.m1info.pvm.group5;

//Negative hashcode is just here to test the hashMap
public class NegativeHashCode {
    private int id;

    public NegativeHashCode(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode(){
        return -Math.abs(id);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj ){
            return true;
        }
        if (obj == null ){
            return true;
        }
        if (!(obj instanceof NegativeHashCode)){
            return false;
        }
        NegativeHashCode other= (NegativeHashCode) obj;
        return this.id==other.getId();
    }
}
