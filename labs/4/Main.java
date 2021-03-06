import mpi.MPI;
import mpi.*;

import java.util.*;

public class Main {
  public static void main(String[] args) throws Exception {
    int data[] = new int[1];

    int buf[] = {1, 3, 5};    

    int count,TAG = 0;

    Status st;

    data[0] = 2016;

    MPI.Init(arg);

    int rank = MPI.COMM_WORLD.Rank();
    int size=MPI.COMM_WORLD.Size();

    if (rank == 0) {
      MPI.COMM_WORLD.Send(data, 0, 1, MPI.INT, 2, TAG);
    }

    if (rank == 1) {
      MPI.COMM_WORLD.Send(buf, 0, buf.length, MPI.INT, 2, TAG);
    }

    if (rank == 2) {
      st = MPI.COMM_WORLD.Probe(0, TAG);  

      int[] back_buf = new int[st.count]; 

      count = st.Get_count(MPI.INT);

      MPI.COMM_WORLD.Recv(back_buf, 0, count, MPI.INT, 0, TAG);

      System.out.print("Rank = 0 ");

      for(int i = 0 ; i < count ; i ++) {
        System.out.print(back_buf[i]+" ");
      }

      st = MPI.COMM_WORLD.Probe(1, TAG);

      int[] back_buf2 = new int[st.count];

      count = st.Get_count(MPI.INT);

      MPI.COMM_WORLD.Recv(back_buf2, 0, count, MPI.INT, 1, TAG);

      System.out.print("Rank = 1 ");

      for(int i = 0 ; i < count ; i ++) {
        System.out.print(back_buf2[i] + " ");
      }
    }

    MPI.Finalize(); 
  }
}
