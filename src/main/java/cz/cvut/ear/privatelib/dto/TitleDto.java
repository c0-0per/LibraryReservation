package cz.cvut.ear.privatelib.dto;

import cz.cvut.ear.privatelib.model.Title;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TitleDto {

    private String nameOfBook;
    private String publishier;
    private String descriptionOfBook;
    private Integer publicationYear;
    private Integer numbersOfItems;
    private Integer numbersOfFreeItems;
    private Integer genreId;
    private List<Integer> authorIds;

    public Title getTitleFromDto(){
        Title title = new Title();
        title.setNameOfBook(nameOfBook);
        title.setPublishier(publishier);
        title.setDescriptionOfBook(descriptionOfBook);
        title.setNumberOfItems(numbersOfItems);
        title.setNumberOfFreeItems(numbersOfFreeItems);
        title.setPublicationYear(publicationYear);

        return title;
    }

}
