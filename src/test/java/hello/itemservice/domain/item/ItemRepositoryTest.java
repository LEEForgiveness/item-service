package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        Item saveItem = itemRepository.save(item);
        //Item 내용 저장
        //then
        Item findItem = itemRepository.findById(item.getId());
        //Item Id 찾기
        assertThat(findItem).isEqualTo(saveItem);
        //findItem해서 찾은 값이랑 saveItem해서 넣은 값이랑 같은지 검증
    }

    @Test
    void findAll(){
        //given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);
        //Item1, Item2 저장
        //when
        List<Item> result = itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        //result의 크기가 우리가 입력한 2개가 맞는 지 확인
        assertThat(result).contains(item1, item2);
        //item1, item2가 result가 포함되어있는지 확인
    }

    @Test
    void updateItem(){
        //given
        Item item = new Item("item1", 10000, 10);

        Item saveItem = itemRepository.save(item);
        Long itemId = saveItem.getId();

        //when
        Item updateParam = new Item("item2", 20000, 30);
        itemRepository.update(itemId, updateParam);

        //then
        Item findItem = itemRepository.findById(itemId);

        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}
