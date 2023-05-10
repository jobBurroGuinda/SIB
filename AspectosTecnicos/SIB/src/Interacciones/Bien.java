/*
 * El Sistema de Inventario de Bienes (SIB) fue desarrollado con la finalidad de
 * aportar una mejora al Centro de Educación Continua Unidad Morelia en cuanto
 * a la administración de los bienes muebles y equipo con los que cuenta.
 * 
 * Este software fue concebido, diseñado y desarrollado por el alumno cuyo seudónimo
 * adoptado es "Emoticón", como proyecto encomendado por el cumplimiento del servicio social.
 *
 */
package Interacciones;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
public class Bien {
    private String claveCABM = null, descripcion = null, marca = null, modelo = null,
            noSerie = null, edificio = null, fechaAsig = null, personaDesig = null, 
            departamento = null, antiguedad = null, detalles = null, año = null;
    
    private int idBien, nivel = 0, local = 0, dia = 0, mes = 0;
    
    private double valor = 0.0;

    
    // Constructor para visualizar los datos en los campos
    // Despues de seleccionar tablas
    public Bien(String descripcion, String marca, String modelo,
            String claveCABM, String noSerie, double valor, String personaDesig,
            String edificio, String departamento, int nivel, int local, String fechaAsig, String detalles){
                    
                this.descripcion = descripcion;
                this.marca = marca;
                this.modelo = modelo;
                this.claveCABM = claveCABM;
                this.noSerie = noSerie;
                this.valor = valor;
                this.personaDesig = personaDesig;
                this.edificio = edificio;
                this.departamento = departamento;
                this.nivel = nivel;
                this.local = local;
                this.fechaAsig = fechaAsig;
                this.detalles = detalles;
    }
    
    
    // Constructor para visualizar datos en la tabla
    public Bien(int idBien, String claveCABM, String descripcion, String marca, String modelo, String noSerie, String personaDesig){
        
        this.idBien = idBien;
        this.claveCABM = claveCABM;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.noSerie = noSerie;
        this.personaDesig = personaDesig;
    }
    
    
    public Bien(){
    }
    
    
    /**
     * @return the claveCABM
     */
    public String getClaveCABM() {
        return claveCABM;
    }

    /**
     * @param claveCABM the claveCABM to set
     */
    public void setClaveCABM(String claveCABM) {
        this.claveCABM = claveCABM;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the noSerie
     */
    public String getNoSerie() {
        return noSerie;
    }

    /**
     * @param noSerie the noSerie to set
     */
    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
    }

    /**
     * @return the edificio
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * @param edificio the edificio to set
     */
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    /**
     * @return the fechaAsig
     */
    public String getFechaAsig() {
        return fechaAsig;
    }

    /**
     * @param fechaAsig the fechaAsig to set
     */
    public void setFechaAsig(String fechaAsig) {
        this.fechaAsig = fechaAsig;
    }

    /**
     * @return the personaDesig
     */
    public String getPersonaDesig() {
        return personaDesig;
    }

    /**
     * @param personaDesig the personaDesig to set
     */
    public void setPersonaDesig(String personaDesig) {
        this.personaDesig = personaDesig;
    }

    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the antiguedad
     */
    public String getAntiguedad() {
        return antiguedad;
    }

    /**
     * @param antiguedad the antiguedad to set
     */
    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    /**
     * @return the detalles
     */
    public String getDetalles() {
        return detalles;
    }

    /**
     * @param detalles the detalles to set
     */
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    /**
     * @return the idBien
     */
    public int getIdBien() {
        return idBien;
    }

    /**
     * @param idBien the idBien to set
     */
    public void setIdBien(int idBien) {
        this.idBien = idBien;
    }

    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the local
     */
    public int getLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(int local) {
        this.local = local;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the año
     */
    public String getAño() {
        return año;
    }

    /**
     * @param año the año to set
     */
    public void setAño(String año) {
        this.año = año;
    }

    /**
     * @return the dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * @return the mes
     */
    public int getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(int mes) {
        this.mes = mes;
    }
    
    
    
}
