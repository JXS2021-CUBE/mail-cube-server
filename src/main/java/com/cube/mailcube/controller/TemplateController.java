package com.cube.mailcube.controller;

import com.cube.mailcube.domain.ErrorCase;
import com.cube.mailcube.domain.ErrorMessage;
import com.cube.mailcube.domain.template.Template;
import com.cube.mailcube.domain.template.TemplateRequestDto;
import com.cube.mailcube.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    @PostMapping("/templates")
    public ResponseEntity<Object> addTemplate(@RequestBody TemplateRequestDto templateRequestDto) {
        if (templateRequestDto.getTitle() == null || templateRequestDto.getContent() == null) {
            return ResponseEntity.badRequest()
                    .body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                            ErrorCase.INVALID_FIELD_ERROR));
        }
        return ResponseEntity.created(
                URI.create("/templates/" + templateService.addTemplate(templateRequestDto))).build();
    }

    // 수정 요방
    @PutMapping("/templates/{id}")
    public ResponseEntity<Object> updateTemplate(@RequestBody TemplateRequestDto templateRequestDto, @PathVariable Long id) {
        return templateService.getTemplateById(id)
                .map(template -> {
                    template.setTitle(templateRequestDto.getTitle());
                    template.setContent(templateRequestDto.getContent());
                    templateService.updateTemplate(template);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.badRequest().body(
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ErrorCase.NO_SUCH_TEMPLATE)));
    }

    @DeleteMapping("/templates/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        Optional<Template> template = templateService.getTemplateById(id);
        System.out.println(template);
        if (!template.isPresent()) {
            return ResponseEntity.badRequest().body(
                    new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ErrorCase.FAIL_FILE_DELETE_ERROR));
        }
        templateService.deleteTemplate(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/templates")
    public ResponseEntity<Object> getAllTemplates() {
        return ResponseEntity.ok().body(templateService.getAllTemplates());
    }

    @GetMapping("/templates/{id}")
    public ResponseEntity<Object> getAllTemplates(@PathVariable Long id) {
        Optional<Template> optionalTemplate = templateService.getTemplateById(id);
        return optionalTemplate.<ResponseEntity<Object>>map(
                template -> ResponseEntity.ok().body(optionalTemplate.get()))
                .orElseGet(() -> ResponseEntity.badRequest().body(
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ErrorCase.NO_SUCH_TEMPLATE)));
    }


}
