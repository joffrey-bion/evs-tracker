package com.jbion.web.evstracker.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Species")
public class Species {

    @Id
    @Column(name = "species_id")
    private Integer id;

    @Column(name = "pokedex_number")
    @NotNull(message = "Pokedex number required")
    @Min(value = 1, message = "The pokedex number can't be below 1.")
    @Max(value = 999, message = "The pokedex number can't be above 999.")
    private Integer pokedexNum;

    @Column(name = "name")
    @NotNull(message = "Species name required")
    private String name;

    @Column(name = "version")
    private String version;

    @Embedded
    @Valid
    @AttributeOverrides({@AttributeOverride(name = "hp", column = @Column(name = "base_hp")),
            @AttributeOverride(name = "att", column = @Column(name = "base_att")),
            @AttributeOverride(name = "def", column = @Column(name = "base_def")),
            @AttributeOverride(name = "spa", column = @Column(name = "base_spa")),
            @AttributeOverride(name = "spd", column = @Column(name = "base_spd")),
            @AttributeOverride(name = "spe", column = @Column(name = "base_spe"))})
    private Stats baseStats;

    public Species() {
        baseStats = new Stats();
    }

    @PrePersist
    @PreUpdate
    private void prePersist() {
        baseStats.prePersist();
    }

    @PostLoad
    private void postLoad() {
        baseStats.postLoad();
    }

    public String getNum3Digits() {
        return String.format("%03d", pokedexNum);
    }

    public String getImgName() {
        return getNum3Digits();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPokedexNum() {
        return pokedexNum;
    }

    public void setPokedexNum(Integer pokedexNum) {
        this.pokedexNum = pokedexNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean hasVersion() {
        return version != null && version.length() > 0;
    }

    public String getFullName() {
        return name + (hasVersion() ? " (" + version + ")" : "");
    }

    public Stats getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(Stats baseStats) {
        this.baseStats = baseStats;
    }

    @Override
    public String toString() {
        return name + "(#" + pokedexNum + ")";
    }
}
