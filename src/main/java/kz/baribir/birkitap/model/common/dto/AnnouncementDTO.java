package kz.baribir.birkitap.model.common.dto;

import kz.baribir.birkitap.model.bookcrossing.vo.AnnouncementVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AnnouncementDTO {
    private String id;
    private String title;
    private String category;
    private String location;
    private int year;
    private List<String> images;
    private String description;
    private String creator;
    private Date createtime;
    private Date updatetime;
    private boolean isFavorite;
    private String favoriteId;

    public AnnouncementVO mapper() {
        AnnouncementVO announcementVO = new AnnouncementVO();
        announcementVO.setId(this.id);
        announcementVO.setTitle(this.title);
        announcementVO.setCategory(this.category);
        announcementVO.setLocation(this.location);
        announcementVO.setYear(this.year);
        announcementVO.setImages(this.images);
        announcementVO.setDescription(this.description);
        announcementVO.setCreator(this.creator);
        announcementVO.setCreatetime(this.createtime.getTime());
        announcementVO.setUpdatetime(this.updatetime.getTime());
        announcementVO.setFavorite(this.isFavorite);
        announcementVO.setFavoriteId(this.favoriteId);

        return announcementVO;
    }

    public void validate() {
        checkNullOrEmpty(title, "title");
        checkNullOrEmpty(category, "category");
        checkNullOrEmpty(description, "description");
    }

    private void checkNullOrEmpty(String str, String paramName) {
        if (str == null) {
            throw new RuntimeException("%s is null".formatted(paramName));
        }


        if (str.trim().isEmpty()) {
            throw new RuntimeException("%s is empty".formatted(paramName));
        }
    }

}
