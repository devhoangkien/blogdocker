package run.halo.app.controller.admin.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import run.halo.app.exception.BadRequestException;
import run.halo.app.model.enums.MigrateType;
import run.halo.app.model.properties.PrimaryProperties;
import run.halo.app.service.MigrateService;
import run.halo.app.service.OptionService;

/**
 * Migrate controller
 *
 * @author ryanwang
 * @date 2019-10-29
 */
@RestController
@RequestMapping("/api/admin/migrations")
public class MigrateController {

    private final MigrateService migrateService;

    private final OptionService optionService;

    public MigrateController(MigrateService migrateService,
        OptionService optionService) {
        this.migrateService = migrateService;
        this.optionService = optionService;
    }

    @PostMapping("halo")
    @ApiOperation("Migrate from Halo")
    public void migrateHalo(@RequestPart("file") MultipartFile file) {
        if (optionService.getByPropertyOrDefault(
            PrimaryProperties.IS_INSTALLED, Boolean.class, false)) {
            throw new BadRequestException("Không thể di chuyển dữ liệu sau khi quá trình khởi chạy blog hoàn tất");
        }
        migrateService.migrate(file, MigrateType.HALO);
    }
}
