package com.unifacs.ads.api_oficina_frota.model;

import com.unifacs.ads.api_oficina_frota.enums.StatusDevolucao;
import com.unifacs.ads.api_oficina_frota.enums.Turno;
import jakarta.persistence.*;



import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_emprestimo")
public class EmprestimoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Instant checkIn;
    private Instant checkOut;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_devolucao", nullable = false)
    private StatusDevolucao statusDevolucao;

    @Enumerated(EnumType.STRING)
    @Column(name = "turno", nullable = false)
    private Turno turnoTrabalho;

    @ManyToOne
    @JoinColumn(name = "id_operador")
    private OperadorModel operador;

    @ManyToOne
    @JoinColumn(name = "id_ferramenta")
    private FerramentaModel ferramenta;

    @ManyToOne
    @JoinColumn(name = "id_os")
    private OrdemServicoModel ordemServico;

    public EmprestimoModel(UUID id, Instant checkIn, Instant checkOut,
                           Turno turnoTrabalho,
                           StatusDevolucao statusDevolucao,
                           OperadorModel operador,
                           FerramentaModel ferramenta,
                           OrdemServicoModel ordemServico) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.turnoTrabalho = turnoTrabalho;
        this.statusDevolucao = statusDevolucao;
        this.operador = operador;
        this.ferramenta = ferramenta;
        this.ordemServico = ordemServico;
    }

    public EmprestimoModel() {
    }

    public Instant getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Instant checkIn) {
        this.checkIn = checkIn;
    }

    public Instant getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Instant checkOut) {
        this.checkOut = checkOut;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StatusDevolucao getStatusDevolucao() {
        return statusDevolucao;
    }

    public void setStatusDevolucao(StatusDevolucao statusDevolucao) {
        this.statusDevolucao = statusDevolucao;
    }

    public Turno getTurnoTrabalho() {
        return turnoTrabalho;
    }

    public void setTurnoTrabalho(Turno turnoTrabalho) {
        this.turnoTrabalho = turnoTrabalho;
    }

    public OperadorModel getOperador() {
        return operador;
    }

    public void setOperador(OperadorModel operador) {
        this.operador = operador;
    }

    public FerramentaModel getFerramenta() {
        return ferramenta;
    }

    public void setFerramenta(FerramentaModel ferramenta) {
        this.ferramenta = ferramenta;
    }

    public OrdemServicoModel getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServicoModel ordemServico) {
        this.ordemServico = ordemServico;
    }
}
