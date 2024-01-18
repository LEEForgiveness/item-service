package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    //@Autowired
    //이렇게 생성자가 딱 1개만 있으면 @Autowired를 생략해도 스프링이 해당 생성자에 @Autowired로 의존관계를 주입해준다.
//    public BasicItemController(ItemRepository itemRepository){
//        this.itemRepository = itemRepository;
//    }
    //@RequiredArgsConstructor써서 생성자 생략 가능

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        //model에 items을 데이터를 추가
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    //@PathVariable는 itemId를 값을 받아옴
    public String item(@PathVariable("itemId") long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@RequestParam("itemName") String itemName,
                       @RequestParam("price") int price,
                       @RequestParam("quantity") Integer quantity,
                       Model model){
        //스프링3.0버전이상에서는 @requestparam에 이름을 적어주는 걸 기본으로 함.

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute(item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){

        itemRepository.save(item);

//        model.addAttribute("item", item);
        //위에 코드를 생략해도 ModelAttribute가 model에 지정한 객체를 자동으로 넣어줌.

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        //("item")을 생략하면 Item클래스 이름을 소문자 item으로 인식함
        itemRepository.save(item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item, Model model){
        //@ModelAttribute자체도 생략 가능
        //@ModelAttribute("item")을 생략하면 Item클래스 이름을 소문자 item으로 인식함
        itemRepository.save(item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item, Model model){
        //@ModelAttribute자체도 생략 가능
        //@ModelAttribute("item")을 생략하면 Item클래스 이름을 소문자 item으로 인식함
        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, Model model, RedirectAttributes redirectAttributes){
        //@ModelAttribute자체도 생략 가능
        //@ModelAttribute("item")을 생략하면 Item클래스 이름을 소문자 item으로 인식함
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model){
        //@PathVariable("itemId")에서 ("itemId") 스프링 3.0이상에서는 생략하면 모호해서 이상하게 됨
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute Item item){
        //@PathVariable("itemId")에서 ("itemId") 스프링 3.0이상에서는 생략하면 모호해서 이상하게 됨
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    //@PostConstruct : 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다.
    //여기서는 간단히 테스트용 테이터를 넣기 위해서 사용했다
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
