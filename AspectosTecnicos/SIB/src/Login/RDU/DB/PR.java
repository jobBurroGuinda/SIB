/*
 * El Sistema de Inventario de Bienes (SIB) fue desarrollado con la finalidad de
 * aportar una mejora al Centro de Educación Continua Unidad Morelia en cuanto
 * a la administración de los bienes muebles y equipo con los que cuenta.
 * 
 * Este software fue concebido, diseñado y desarrollado por el alumno cuyo seudónimo
 * adoptado es "Emoticón", como proyecto encomendado por el cumplimiento del servicio social.
 *
 */
package Login.RDU.DB;

import java.io.Serializable;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
public class PR implements Serializable {
        private String pregunta;
        private String respuesta;

    public PR(String pregunta) {
        this.pregunta = pregunta;
    }

    PR() {
        
    }

    
    /**
     * @return the pregunta
     */
    public String getPregunta() {
        return pregunta;
    }

    /**
     * @param pregunta the pregunta to set
     */
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * @return the respuesta
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta the respuesta to set
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

        
        
        
        
}
