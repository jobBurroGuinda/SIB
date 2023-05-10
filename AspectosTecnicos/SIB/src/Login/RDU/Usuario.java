/*
 * El Sistema de Inventario de Bienes (SIB) fue desarrollado con la finalidad de
 * aportar una mejora al Centro de Educación Continua Unidad Morelia en cuanto
 * a la administración de los bienes muebles y equipo con los que cuenta.
 * 
 * Este software fue concebido, diseñado y desarrollado por el alumno cuyo seudónimo
 * adoptado es "Emoticón", como proyecto encomendado por el cumplimiento del servicio social.
 *
 */
package Login.RDU;


import java.io.Serializable;


/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
public class Usuario implements Serializable {
    
    private String nomUsuario;
    private String password;
    private String pregunta1;
    private String pregunta2;
    private String pregunta3;
    private String pregunta4;
    private String respuesta1;
    private String respuesta2;
    private String respuesta3;
    private String respuesta4;

    
    public Usuario(String nomUsuario, String password, String pregunta1, 
            String pregunta2, String pregunta3, String pregunta4, String respuesta1,
            String respuesta2, String respuesta3, String respuesta4) {
        this.nomUsuario = nomUsuario;
        this.password = password;
        this.pregunta1 = pregunta1;
        this.pregunta2 = pregunta2;
        this.pregunta3 = pregunta3;
        this.pregunta4 = pregunta4;
        this.respuesta1 = respuesta1;
        this.respuesta2 = respuesta2;
        this.respuesta3 = respuesta3;
        this.respuesta4 = respuesta4;
    }
    
    public Usuario(){
        
    }

    
    /**
     * @return the nomUsuario
     */
    public String getNomUsuario() {
        return nomUsuario;
    }

    /**
     * @param nomUsuario the nomUsuario to set
     */
    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the pregunta1
     */
    public String getPregunta1() {
        return pregunta1;
    }

    /**
     * @param pregunta1 the pregunta1 to set
     */
    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

    /**
     * @return the pregunta2
     */
    public String getPregunta2() {
        return pregunta2;
    }

    /**
     * @param pregunta2 the pregunta2 to set
     */
    public void setPregunta2(String pregunta2) {
        this.pregunta2 = pregunta2;
    }

    /**
     * @return the pregunta3
     */
    public String getPregunta3() {
        return pregunta3;
    }

    /**
     * @param pregunta3 the pregunta3 to set
     */
    public void setPregunta3(String pregunta3) {
        this.pregunta3 = pregunta3;
    }

    /**
     * @return the pregunta4
     */
    public String getPregunta4() {
        return pregunta4;
    }

    /**
     * @param pregunta4 the pregunta4 to set
     */
    public void setPregunta4(String pregunta4) {
        this.pregunta4 = pregunta4;
    }

    /**
     * @return the respuesta1
     */
    public String getRespuesta1() {
        return respuesta1;
    }

    /**
     * @param respuesta1 the respuesta1 to set
     */
    public void setRespuesta1(String respuesta1) {
        this.respuesta1 = respuesta1;
    }

    /**
     * @return the respuesta2
     */
    public String getRespuesta2() {
        return respuesta2;
    }

    /**
     * @param respuesta2 the respuesta2 to set
     */
    public void setRespuesta2(String respuesta2) {
        this.respuesta2 = respuesta2;
    }

    /**
     * @return the respuesta3
     */
    public String getRespuesta3() {
        return respuesta3;
    }

    /**
     * @param respuesta3 the respuesta3 to set
     */
    public void setRespuesta3(String respuesta3) {
        this.respuesta3 = respuesta3;
    }

    /**
     * @return the respuesta4
     */
    public String getRespuesta4() {
        return respuesta4;
    }

    /**
     * @param respuesta4 the respuesta4 to set
     */
    public void setRespuesta4(String respuesta4) {
        this.respuesta4 = respuesta4;
    }
    
    
    
}
