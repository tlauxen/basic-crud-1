//Entity (must be like java pojo)
function Pais(data) {
    this.id = data.id;
    this.sigla = data.sigla;
    this.nome = data.nome;
}

//Entity (must be like java pojo)
function Estado(data) {
    this.id = data.id;
    this.pais = new Pais(data.pais);
    this.sigla = data.sigla;
    this.nome = data.nome;
}

