package app.article.domain;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author ebin
 */
@Table(name = "customers")
public class customer {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "email")
    public String email;

    @NotNull
    @Column(name = "first_name")
    public String first_name;

    @NotNull
    @Column(name = "last_name")
    public String last_name;

    @NotNull
    @Column(name = "created_time")
    public LocalDateTime created_time;

    @NotNull
    @Column(name = "created_by")
    public String created_by;

    @NotNull
    @Column(name = "updated_time")
    public LocalDateTime updated_time;

    @NotNull
    @Column(name = "updated_by")
    public String updated_by;

}
