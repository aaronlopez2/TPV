package dbo;

public final class Articulos {
    private String idArticulo,marca,talla,modelo;
    private float coste;
    private int cantidad;

    public Articulos(String idArticulo, int cantidad) {
        this.idArticulo = idArticulo;
        this.cantidad = cantidad;
    }
    public Articulos(String idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Articulos(String idArticulo, String marca, String talla, String modelo, float coste, int cantidad) {
        this.idArticulo = idArticulo;
        this.marca = marca;
        this.talla = talla;
        this.modelo = modelo;
        this.coste = coste;
        this.cantidad = cantidad;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public float getCoste() {
        return coste;
    }

    public void setCoste(float coste) {
        this.coste = coste;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
