package org.project.omwp.service;

import lombok.RequiredArgsConstructor;
import org.project.omwp.entity.*;
import org.project.omwp.dto.OrderlistDto;
import org.project.omwp.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderlistService {

    private final OrderlistRepository orderlistRepository;
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ImgRepository imgRepository;

    public int addOrder(Long wishId) {
        WishEntity wishEntity = wishRepository.findById(wishId).get();
        OrderlistEntity orderlistEntity =
                OrderlistEntity.toOrderlistEntity(wishEntity.getMemberEntity(), wishEntity.getProductEntity());
        Long id = orderlistRepository.save(orderlistEntity).getOrderlistId();

        if (orderlistRepository.findById(id) == null) {
            return 0;
        }
        return 1;
    }


    @Transactional
    public OrderlistEntity insertOrder(Long userId, Long productId) throws IOException {

        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);

        ProductEntity productEntity = optionalProductEntity.get();

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(userId);
        MemberEntity memberEntity = optionalMemberEntity.get();



        OrderlistEntity orderlistEntity = new OrderlistEntity();

        orderlistEntity.setOrderlistId(orderlistEntity.getOrderlistId());
        orderlistEntity.setProductEntity(productEntity);
        orderlistEntity.setMemberEntity(memberEntity);
        orderlistEntity.setOrderlistStatus(1);
        orderlistEntity.setOrderlistCount(1);

        orderlistRepository.save(orderlistEntity);

        return orderlistEntity;


    }

    public List<OrderlistDto> selectAllOrder(Long userId) {

        List<OrderlistDto> orderlistDtoList = new ArrayList<>();

        List<OrderlistEntity> orderlistEntityList = orderlistRepository.findAllByuserId(userId);

        for (OrderlistEntity orderlistEntity : orderlistEntityList) {

            orderlistDtoList.add(OrderlistDto.toOrderlistDto(orderlistEntity));

        }

        return orderlistDtoList;
    }

    public Page<OrderlistDto> selectAllOrder2(Long userId, Pageable pageable) {
        Page<OrderlistEntity> orderlistEntities =
                orderlistRepository.findAllByuserId2(userId, pageable);

        return orderlistEntities.map(OrderlistDto::toOrderlistDto);
    }

    @Transactional
    public void cancelOrder(Long id) {

        OrderlistDto orderlistDto = new OrderlistDto();

        Optional<OrderlistEntity> optionalOrderlistEntity = orderlistRepository.findById(id);

        OrderlistEntity orderlistEntity = optionalOrderlistEntity.get();

        orderlistEntity.setOrderlistStatus(0);

        orderlistRepository.save(orderlistEntity);

    }


    public List<OrderlistDto> selectAllCancel(Long userId) {

        List<OrderlistDto> orderlistDtoList = new ArrayList<>();

        List<OrderlistEntity> orderlistEntityList = orderlistRepository.findAllCancelByUserId(userId);

        for (OrderlistEntity orderlistEntity : orderlistEntityList) {

            orderlistDtoList.add(OrderlistDto.toOrderlistDto(orderlistEntity));

        }

        return orderlistDtoList;
    }

    public void deleteCancelList(OrderlistDto orderlistDto) {

        Long id = orderlistDto.getOrderlistId();

        Optional<OrderlistEntity> optionalOrderlistEntity = orderlistRepository.findById(id);

        OrderlistEntity orderlistEntity = optionalOrderlistEntity.get();

        orderlistRepository.delete(orderlistEntity);

    }

    //    주문내역 조회
    public Page<OrderlistDto> searchListDo(String type, String keyword, Pageable pageable) {
        if (type.equals("productId")) {
            Page<OrderlistEntity> orderlistEntity =
                    orderlistRepository.findAllByProductId(keyword, pageable);
            return orderlistEntity.map(OrderlistDto::orderlistDto);
        } else if (type.equals("productName")) {
            Page<OrderlistEntity> orderlistEntity =
                    orderlistRepository.findByProductNameContaining(keyword, pageable);
            return orderlistEntity.map(OrderlistDto::orderlistDto);
        } else if (type.equals("userName")) {
            Page<OrderlistEntity> orderlistEntity =
                    orderlistRepository.findByUserNameContaining(keyword, pageable);
            return orderlistEntity.map(OrderlistDto::orderlistDto);
        } else if (type.equals("userEmail")) {
            Page<OrderlistEntity> orderlistEntity =
                    orderlistRepository.findByUserEmailContaining(keyword, pageable);
            return orderlistEntity.map(OrderlistDto::orderlistDto);
        }

        return null;
    }

    //////////////주문 조회 및 검색 할때/////////////////////

    //전체 페이지 불러오기
    public Page<OrderlistDto> selectOrderlist(Pageable pageable) {
        Page<OrderlistEntity> orderlistEntity =
                orderlistRepository.findAllOrders(pageable);

        return orderlistEntity.map(OrderlistDto::orderlistDto);
    }

    //주문번호 검색
    public Page<OrderlistDto> searchOrderlistDo(Long orderlistId, Pageable pageable) {
        Page<OrderlistEntity> orderlistEntity =
                orderlistRepository.searchRecodeNumber(orderlistId, pageable);

        return orderlistEntity.map(OrderlistDto::orderlistDto);
    }

    //상품번호 검색
    public Page<OrderlistDto> searchProductId(Long productId, Pageable pageable) {
        Page<OrderlistEntity> orderlistEntityPage =
                orderlistRepository.searchRecodeNumber2(productId, pageable);

        return orderlistEntityPage.map(OrderlistDto::orderlistDto);
    }

    //상품명 검색
    public Page<OrderlistDto> searchProductName(String keyword, Pageable pageable) {
        Page<OrderlistEntity> orderlistEntityPage =
                orderlistRepository.searchRecodeProductName(keyword, pageable);

        return orderlistEntityPage.map(OrderlistDto::orderlistDto);
    }

    //회원유저 검색
    public Page<OrderlistDto> searchUserName(String keyword, Pageable pageable) {

        Page<OrderlistEntity> orderlistEntityPage =
                orderlistRepository.searchRecode1(keyword, pageable);

        return orderlistEntityPage.map(OrderlistDto::orderlistDto);
    }

    //회원이메일검색
    public Page<OrderlistDto> searchUserEmail(String keyword, Pageable pageable) {

        Page<OrderlistEntity> orderlistEntityPage =
                orderlistRepository.searchRecode2(keyword, pageable);

        return orderlistEntityPage.map(OrderlistDto::orderlistDto);
    }

    // 구매내역 페이징
    public Page<OrderlistDto> OrderlistDtoList(Long userId, Pageable pageable) {

        Page<OrderlistEntity>  orderlistEntity= orderlistRepository.findAllByuserId2(userId,pageable);
        Page<OrderlistDto> orderlistDto = orderlistEntity.map(OrderlistDto::toOrderlistDto);

        return orderlistDto;
    }
}
