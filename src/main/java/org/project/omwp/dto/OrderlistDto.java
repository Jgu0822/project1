package org.project.omwp.dto;

import lombok.*;
import org.project.omwp.entity.OrderlistEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderlistDto {

    private Long orderlistId;
    private int orderlistCount;
//    private int orderlistPrice;
    private LocalDateTime orderlistDate;
    private int orderlistStatus;

    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;

    private Long productId;
    private String productType;
    private String productName;
    private int productPrice;
    private String productDesc;

    private String imgNewName;
    private int attachImg; //이미지 유무(1,0)


    public static OrderlistDto orderlistDto(OrderlistEntity orderlistEntity) {
        OrderlistDto orderlistDto = new OrderlistDto();

        orderlistDto.setOrderlistId(orderlistEntity.getOrderlistId());
        orderlistDto.setOrderlistCount(orderlistEntity.getOrderlistCount());
        orderlistDto.setOrderlistDate(orderlistEntity.getOrderlistDate());
        orderlistDto.setOrderlistStatus(orderlistEntity.getOrderlistStatus());
        orderlistDto.setUserId(orderlistEntity.getMemberEntity().getUserId());
        orderlistDto.setUserName(orderlistEntity.getMemberEntity().getUserName());
        orderlistDto.setUserEmail(orderlistEntity.getMemberEntity().getUserEmail());
        orderlistDto.setUserPhone(orderlistEntity.getMemberEntity().getUserPhone());
        orderlistDto.setProductId(orderlistEntity.getProductEntity().getProductId());
        orderlistDto.setProductType(orderlistEntity.getProductEntity().getProductType());
        orderlistDto.setProductName(orderlistEntity.getProductEntity().getProductName());
        orderlistDto.setProductPrice(orderlistEntity.getProductEntity().getProductPrice());

        return orderlistDto;
    }

    public static OrderlistDto toOrderlistDto(OrderlistEntity orderlistEntity) {
        OrderlistDto orderlistDto = new OrderlistDto();

        orderlistDto.setOrderlistId(orderlistEntity.getOrderlistId());
        orderlistDto.setOrderlistCount(orderlistEntity.getOrderlistCount());
        orderlistDto.setOrderlistDate(orderlistEntity.getOrderlistDate());
        orderlistDto.setOrderlistStatus(orderlistEntity.getOrderlistStatus());
        orderlistDto.setUserId(orderlistEntity.getMemberEntity().getUserId());
        orderlistDto.setProductId(orderlistEntity.getProductEntity().getProductId());
        orderlistDto.setProductType(orderlistEntity.getProductEntity().getProductType());
        orderlistDto.setProductName(orderlistEntity.getProductEntity().getProductName());
        orderlistDto.setProductPrice(orderlistEntity.getProductEntity().getProductPrice());
        orderlistDto.setProductDesc(orderlistEntity.getProductEntity().getProductDesc());
        if (orderlistEntity.getProductEntity().getAttachImg() == 0){
            orderlistDto.setAttachImg(orderlistEntity.getProductEntity().getAttachImg());

        }else{ //이미지가 있을때
            orderlistDto.setAttachImg(orderlistEntity.getProductEntity().getAttachImg());

            //이미지 불러오기
            orderlistDto.setImgNewName(orderlistEntity.getProductEntity().getImgEntities().get(0).getImgNewName());

        }

        return orderlistDto;
    }

}
