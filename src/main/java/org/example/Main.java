package org.example;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        //TIP 当文本光标位于高亮显示的文本处时按 <shortcut actionId="ShowIntentionActions"/>
        // 查看 IntelliJ IDEA 建议如何修正。
        System.out.print("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP 按 <shortcut actionId="Debug"/> 开始调试代码。我们已经设置了一个 <icon src="AllIcons.Debugger.Db_set_breakpoint"/> 断点
            // 但您始终可以通过按 <shortcut actionId="ToggleLineBreakpoint"/> 添加更多断点。
            System.out.println("i = " + i);
        }

        MiniLooper.prepare();

        MiniHandler handler = new MiniHandler() {
            @Override
            public void handleMessage(Message msg) {
                System.out.println("handleMessage, msg received at " + msg.when);
            }
        };

        handler.post(() -> System.out.println("run, msg received at " + System.currentTimeMillis()));
        handler.postDelayed(() -> System.out.println("runDelayed, msg received at " + System.currentTimeMillis()), 1000);
        handler.sendMessage(2, 5, "Low priority message", () -> System.out.println("Low priority message"));
        handler.sendMessage(1, -1, "High priority message", () -> System.out.println("High priority message"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        MiniLooper.quit();
    }
}