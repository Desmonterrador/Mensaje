import java.util.Random;
public class Main {
    public static void main(String[] args) {
        Mensaje mensaje = new Mensaje();
        (new Thread(new Escritor(mensaje))).start();
        (new Thread(new Lector(mensaje))).start();
    }
}
class Mensaje{
    private String mensaje;
    private boolean vacio = true;
    public synchronized String leer(){
        while(vacio){
            try {
                wait();
            } catch (InterruptedException ie) {

            }
        }
        vacio = true;
        notifyAll();
        return mensaje;
    }
    public synchronized void escribir(String mensaje){
        while(!vacio){
            try {
                wait();
            } catch (InterruptedException ie) {
                
            }
        }
        vacio = false;
        this.mensaje = mensaje;
        notifyAll();
    }
}
class Escritor implements Runnable{
    private Mensaje mensaje;
    public Escritor(Mensaje mensaje){
        this.mensaje = mensaje;
    }
    @Override
    public void run() {
        String mensajes[] = {
            "Martinillo, Martinillo", "¿Dónde estás?¿Dónde estás?", "Toca la campana, Toca la campana","Din don dan Din don dan"
        };
        Random random = new Random();
        for(int i = 0; i < mensajes.length; i++){
            mensaje.escribir(mensajes[i]);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ie) {
                
            }
        }
        mensaje.escribir("Terminado");
    }
}
class Lector implements Runnable{
    private Mensaje mensaje;
    public Lector(Mensaje mensaje){
        this.mensaje = mensaje;
    }
    @Override
    public void run() {
        Random random = new Random();
        for(String UltimoMensaje = mensaje.leer(); !UltimoMensaje.equals("Terminado"); UltimoMensaje=mensaje.leer()){
            System.out.println(UltimoMensaje);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException ie) {
                
            }
        }
    }
}