package shop.mtcoding.blog;

import org.junit.jupiter.api.Test;

public class PagingTest {

    @Test
    public void count(){
        int totalCount = 7;
        int curruentPage = 2;
        int totalPage = totalCount/3 ;
        if(totalCount%3==0){
            int lastPage = totalPage -1 ;
            boolean last = (curruentPage==lastPage? true:false);

            System.out.println("1 :" + last);

        } else if(totalCount%3!=0){
            int lastPage = totalPage ;
            boolean last = (curruentPage==lastPage? true:false);
            System.out.println("2 :" + last);
        }
    }
}
