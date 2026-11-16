---
name: email_giangvien
description: >-
  Hỗ trợ viết thư từ, thông điệp, văn bản gửi giảng viên, thầy cô giáo
  về các vấn đề học tập trong trường học: (1) Xin nghỉ học, xin phép vắng mặt, báo bận, bệnh;
  (2) Xin gia hạn deadline, lùi hạn nộp bài, hoãn nộp bài tập lớn, đồ án, lab, bài tập về nhà, nộp trễ;
  (3) Xin đổi nhóm, chuyển nhóm, đổi thành viên, ghép nhóm làm bài tập;
  (4) Hỏi bài, thắc mắc kiến thức bài giảng, nhờ giải đáp lỗi code/bài tập, xin tài liệu, xin lịch hẹn gặp trao đổi trực tiếp hoặc online (office hours).
  Kích hoạt NGAY CẢ KHI người dùng KHÔNG nhắc đến từ "email", "thư", "mail" trong câu hỏi
  (ví dụ: "mai em xin nghỉ", "cô cho em lùi hạn bài tập với", "muốn chuyển sang nhóm bạn Nam", "chỗ này code bị lỗi nhờ thầy giải thích").
  Tự động kiểm tra và thu thập đủ 4 thông tin bắt buộc: Họ tên, MSSV, Lớp/Mã lớp, Lý do.
---

# Kỹ năng Soạn Thư Liên Hệ Giảng Viên (`email_giangvien`)

Kỹ năng này giúp sinh viên tạo nhanh các văn bản, thư từ liên hệ với Giảng viên / Thầy Cô giáo chuẩn mực, lịch sự, đúng quy cách sư phạm trong môi trường học thuật.

---

## 1. Bốn Thông Tin Bắt Buộc Cần Có (Core Information)

Bất kể người dùng liên hệ về vấn đề gì, Agent **phải đảm bảo** nội dung có đầy đủ 4 thông tin sau:

| STT | Thông tin bắt buộc | Ý nghĩa / Ví dụ |
| :---: | :--- | :--- |
| **1** | **Họ và tên** | Tên đầy đủ của sinh viên (Ví dụ: *Nguyễn Văn A*) |
| **2** | **MSSV** | Mã số sinh viên (Ví dụ: *21110123*) |
| **3** | **Lớp / Mã lớp** | Lớp sinh hoạt hoặc Mã lớp học phần (Ví dụ: *21DTHA1* hoặc *COMP1024_01*) |
| **4** | **Lý do** | Lý do cụ thể, hợp lý, trung thực, mang tính xây dựng |

> **Quy tắc xử lý của Agent:**
> - Nếu người dùng đã cung cấp đủ 4 thông tin trong câu hỏi: Lập tức điền vào mẫu và trả kết quả hoàn chỉnh.
> - Nếu người dùng còn thiếu 1 hoặc nhiều thông tin (ví dụ chỉ nói: *"Soạn giúp em đơn xin dời deadline vì bị sốt"*):
>   1. Tạo bản nháp hoàn chỉnh với các vị trí trống được đánh dấu rõ ràng dạng `[Họ và tên]`, `[MSSV]`, `[Mã lớp]`, `[Lý do cụ thể]`.
>   2. Nhắc người dùng ngắn gọn: *"Bạn có thể cung cấp thêm Tên, MSSV, Lớp để mình điền tự động hoặc tự điền vào các phần trong ngoặc vuông `[...]` nhé."*

---

## 2. Khi Nào Kích Hoạt Kỹ Năng (Triggers - Không Cần Chữ "Email")

Agent kích hoạt kỹ năng này ngay khi phát hiện **ý định liên hệ, trao đổi, xin phép giảng viên/thầy cô**, dù người dùng **hoàn toàn không dùng từ "email" hay "thư"**:

### Nhóm 1: Xin nghỉ học / vắng mặt
- *"Thầy ơi mai em bị sốt không lên lớp được, cho em xin nghỉ một buổi ạ"*
- *"Hôm nay nhà em có việc đột xuất, em muốn xin cô vắng tiết Lập trình di động"*
- *"Mai em bị trùng lịch thi chứng chỉ, xin thầy cho em vắng mặt"*
- *"I am sick today and cannot attend the lecture, please help me inform the professor"*

### Nhóm 2: Gia hạn deadline / xin nộp muộn
- *"Thầy cho nhóm em lùi hạn nộp bài tập lớn sang cuối tuần được không ạ"*
- *"Máy tính em bị hỏng ổ cứng nên chưa kịp push code bài lab, xin cô cho em nộp muộn 2 ngày"*
- *"Xin thầy gia hạn thời gian nộp đồ án cuối kỳ"*
- *"Can I request an extension for Assignment 2 from Dr. John?"*

### Nhóm 3: Đổi nhóm / chuyển nhóm
- *"Em muốn chuyển từ nhóm 2 sang nhóm 5 môn Lập trình thiết bị di động"*
- *"Nhóm em với nhóm bạn Nam muốn đổi một thành viên cho nhau thì viết cho thầy như nào"*
- *"Do lịch làm việc bị trùng nên em muốn xin thầy đổi sang nhóm học khác"*
- *"Xin phép cô cho em chuyển nhóm bài tập lớn"*

### Nhóm 4: Hỏi bài / thắc mắc kiến thức / hẹn gặp
- *"Thầy giải thích giúp em phần State Management trong Compose với ạ, em đọc tài liệu mãi không hiểu"*
- *"Code đồ án của em bị crash ở đoạn gọi API Firebase, nhờ cô xem giúp em lỗi này"*
- *"Em muốn xin thầy một buổi gặp trực tiếp vào thứ 5 để nhờ thầy định hướng đề tài tốt nghiệp"*
- *"Em có thắc mắc về điểm thi giữa kỳ môn Cơ sở dữ liệu"*

---

## 3. Quy Trình 4 Bước Của Agent

1. **Xác định loại yêu cầu:** Phân loại vào 1 trong 4 nhóm: (1) Xin nghỉ, (2) Gia hạn deadline, (3) Đổi nhóm, (4) Hỏi bài / Hẹn tư vấn.
2. **Trích xuất thông tin:** Thu thập Họ tên, MSSV, Lớp/Mã lớp, Lý do, Tên môn học, Tên giảng viên (nếu có).
3. **Lựa chọn mẫu phù hợp:** Áp dụng cấu trúc chuẩn mực: Tiêu đề rõ ràng $\rightarrow$ Kính gửi $\rightarrow$ Giới thiệu $\rightarrow$ Nội dung chính $\rightarrow$ Cam kết/Đề xuất $\rightarrow$ Lời cảm ơn $\rightarrow$ Chữ ký.
4. **Trả kết quả:** Cung cấp định dạng xem trước thuận tiện để người dùng chỉ việc sao chép (copy-paste) và gửi đi.

---

## 4. Các Mẫu Văn Bản Chuẩn Mực (Templates)

### Phần 1: Xin Nghỉ Học / Vắng Mặt

#### Mẫu 1.1: Xin nghỉ vì lý do sức khỏe / bệnh
```text
Tiêu đề: [Tên Môn Học] - [Mã Lớp HP] - [MSSV] - [Họ và Tên] - Xin phép vắng mặt buổi học ngày [DD/MM/YYYY]

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Em tên là: [Họ và Tên]
Mã số sinh viên: [MSSV]
Hiện đang theo học lớp học phần [Tên Môn Học], mã lớp [Mã Lớp HP], ca học [Thứ..., Tiết...].

Em viết thư này kính xin phép Thầy/Cô cho em được vắng mặt trong buổi học ngày [Thứ..., ngày DD/MM/YYYY].
Lý do: Hiện tại sức khỏe của em không tốt ([nêu triệu chứng cụ thể, ví dụ: em bị sốt xuất huyết / cảm cúm nặng cần nghỉ ngơi và theo dõi theo chỉ định của bác sĩ]), nên em không thể tham gia buổi học trên lớp.

Trong thời gian nghỉ học, em cam kết:
1. Chủ động mượn vở và hỏi lại bạn bè trong lớp để ghi chép lại đầy đủ kiến thức.
2. Tự nghiên cứu slide bài giảng và tài liệu môn học.
3. Hoàn thành đầy đủ các bài tập được giao của buổi học này và nộp đúng hạn.

(Nếu có minh chứng y tế: Em xin phép gửi đính kèm đơn thuốc/giấy khám bệnh để Thầy/Cô xác thực ạ.)

Em kính mong Thầy/Cô xem xét và tạo điều kiện cho em. Em xin chân thành cảm ơn Thầy/Cô!

Kính chúc Thầy/Cô nhiều sức khỏe và công tác tốt.

Kính thư,
[Họ và Tên]
MSSV: [MSSV]
Lớp: [Mã Lớp]
Số điện thoại: [Số điện thoại liên hệ]
```

#### Mẫu 1.2: Xin nghỉ vì việc gia đình đột xuất / việc bất khả kháng
```text
Tiêu đề: [Tên Môn Học] - [Mã Lớp HP] - [MSSV] - [Họ và Tên] - Xin phép vắng mặt buổi học ngày [DD/MM/YYYY]

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Em tên là: [Họ và Tên]
Mã số sinh viên: [MSSV]
Sinh viên lớp [Mã Lớp], hiện đang theo học môn [Tên Môn Học] (Mã lớp học phần: [Mã Lớp HP]).

Em viết thư này kính xin phép Thầy/Cô cho em được nghỉ buổi học vào ngày [Thứ..., ngày DD/MM/YYYY] do gia đình em có việc phát sinh đột xuất không thể sắp xếp khác được ([ví dụ: gia đình có việc hiếu / đưa người thân đi cấp cứu / có việc quan trọng tại quê]).

Để không bị gián đoạn tiến độ học tập, em xin cam kết:
- Tự giác xem lại bài giảng và tài liệu học tập của buổi vắng mặt.
- Liên hệ các thành viên trong lớp/nhóm để cập nhật nội dung Thầy/Cô dặn dò.
- Làm và nộp bài tập đầy đủ theo đúng yêu cầu của Thầy/Cô.

Kính mong Thầy/Cô thông cảm và cho phép em được vắng mặt buổi học này. Em xin chân thành cảm ơn Thầy/Cô!

Kính thư,
[Họ và Tên]
MSSV: [MSSV]
Lớp: [Mã Lớp]
```

---

### Phần 2: Xin Gia Hạn Deadline / Xin Nộp Muộn

#### Mẫu 2.1: Cá nhân xin gia hạn nộp bài tập / Lab / Báo cáo
```text
Tiêu đề: [Tên Môn Học] - [Mã Lớp HP] - [MSSV] - [Họ và Tên] - Xin gia hạn nộp [Tên bài tập/Lab]

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Em tên là: [Họ và Tên]
Mã số sinh viên: [MSSV]
Sinh viên lớp: [Mã Lớp], hiện đang học môn [Tên Môn Học] (Mã học phần: [Mã Lớp HP]).

Em viết thư này kính mong Thầy/Cô xem xét cho phép em được xin gia hạn nộp bài tập [Tên bài tập/Lab số...] thêm [số ngày, ví dụ: 02 ngày], tức hạn nộp mới đến [Giờ, ngày DD/MM/YYYY] (thay vì deadline cũ là [Giờ, ngày DD/MM/YYYY]).

Lý do: [Nêu trung thực, cụ thể, ví dụ:
- Trong quá trình triển khai bài lab, em gặp sự cố hỏng ổ cứng máy tính khiến một phần mã nguồn chưa kịp đồng bộ lên Git; em đã khôi phục lại máy và đang gấp rút làm lại phần còn thiếu.
- Hoặc: Mấy ngày qua em bị ốm sốt nặng, ảnh hưởng đến tiến độ hoàn thành bài tập đúng hạn.]

Hiện tại em đã hoàn thành được khoảng [ví dụ: 70%] khối lượng công việc. Em cam kết sẽ tận dụng thời gian được gia hạn để hoàn thiện bài chỉn chu nhất và nộp đúng thời hạn mới.

Em xin gửi đính kèm [ảnh chụp tiến độ bài làm / ảnh minh chứng sự cố] để Thầy/Cô tiện xem xét.

Em rất mong nhận được sự thấu hiểu và tạo điều kiện từ Thầy/Cô. Em xin chân thành cảm ơn Thầy/Cô!

Kính thư,
[Họ và Tên]
MSSV: [MSSV]
Lớp: [Mã Lớp]
```

#### Mẫu 2.2: Đại diện nhóm xin gia hạn nộp Đồ án / Bài tập lớn
```text
Tiêu đề: [Tên Môn Học] - [Mã Lớp HP] - Nhóm [Số nhóm] - Xin gia hạn nộp Đồ án [Tên Đề Tài]

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Em tên là: [Họ và Tên], MSSV: [MSSV], sinh viên lớp [Mã Lớp].
Em đại diện cho Nhóm [Số nhóm] lớp học phần [Tên Môn Học] (Mã HP: [Mã Lớp HP]).

Danh sách thành viên nhóm gồm:
1. [Họ và tên] - MSSV: [MSSV] (Trưởng nhóm)
2. [Họ và tên] - MSSV: [MSSV]
3. [Họ và tên] - MSSV: [MSSV]

Chúng em viết thư này kính xin phép Thầy/Cô tạo điều kiện cho nhóm được gia hạn thời gian nộp sản phẩm đồ án [Tên Đề tài/Đồ án] đến [Giờ, ngày DD/MM/YYYY] (deadline quy định hiện tại là [Giờ, ngày DD/MM/YYYY]).

Lý do: [Nêu chi tiết khó khăn nhóm gặp phải, ví dụ:
- Trong quá trình tích hợp module thanh toán và API backend, nhóm phát sinh lỗi bất đồng bộ dữ liệu nghiêm trọng chưa thể xử lý dứt điểm trước deadline.
- Nhóm cần thêm 2 ngày để sửa triệt để lỗi logic này và bổ sung các ca kiểm thử nhằm đảm bảo sản phẩm vận hành ổn định nhất.]

Tiến độ hiện tại của nhóm đã đạt [ví dụ: 85%]. Nhóm xin đính kèm báo cáo tiến độ và link repository mã nguồn hiện tại để Thầy/Cô nắm được tình hình làm việc thực tế của nhóm.

Nhóm chúng em kính mong Thầy/Cô xem xét và đồng ý cho nhóm được dời hạn nộp. Chúng em xin chân thành cảm ơn Thầy/Cô!

Đại diện nhóm,
[Họ và Tên]
MSSV: [MSSV]
Lớp: [Mã Lớp]
Số điện thoại: [Số điện thoại]
```

---

### Phần 3: Xin Đổi Nhóm / Chuyển Nhóm

#### Mẫu 3.1: Cá nhân xin chuyển sang nhóm khác do lịch sinh hoạt/làm việc
```text
Tiêu đề: [Tên Môn Học] - [Mã Lớp HP] - [MSSV] - [Họ và Tên] - Xin chuyển nhóm bài tập lớn

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Em tên là: [Họ và Tên]
Mã số sinh viên: [MSSV]
Lớp: [Mã Lớp]
Hiện đang học môn [Tên Môn Học], mã lớp học phần: [Mã Lớp HP].

Trong danh sách phân nhóm bài tập lớn, hiện tại em đang thuộc Nhóm [Số nhóm hiện tại]. Em viết thư này kính xin phép Thầy/Cô cho em được chuyển sang sinh hoạt tại Nhóm [Số nhóm muốn chuyển đến] (Nhóm của bạn [Tên trưởng nhóm mới], MSSV: [MSSV]).

Lý do: [Nêu lý do thuyết phục, ví dụ:
- Lịch học phụ đạo và lịch làm thêm của em bị trùng vào khung giờ họp cố định của Nhóm [Số cũ], khiến em không thể tham gia họp nhóm đầy đủ, trong khi Nhóm [Số mới] có lịch họp phù hợp với thời gian biểu của em.
- Hoặc: Đề tài nghiên cứu của Nhóm [Số mới] phù hợp hơn với định hướng chuyên ngành và kỹ năng hiện tại của em.]

Em đã chủ động trao đổi trước với cả hai nhóm:
- Nhóm trưởng Nhóm [Số cũ] (Bạn [Tên]) đã nắm thông tin và đồng ý cho em rút để không ảnh hưởng phân công nhiệm vụ.
- Nhóm trưởng Nhóm [Số mới] (Bạn [Tên]) và các thành viên đã đồng ý tiếp nhận em vào nhóm.

Kính mong Thầy/Cô xem xét và cập nhật danh sách nhóm cho em ạ. Em xin trân trọng cảm ơn Thầy/Cô!

Kính thư,
[Họ và Tên]
MSSV: [MSSV]
Lớp: [Mã Lớp]
```

#### Mẫu 3.2: Hoán đổi thành viên giữa 2 nhóm (Đổi chéo)
```text
Tiêu đề: [Tên Môn Học] - [Mã Lớp HP] - Đơn xin hoán đổi thành viên giữa Nhóm [X] và Nhóm [Y]

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Chúng em là sinh viên lớp [Mã Lớp], hiện đang theo học môn [Tên Môn Học] - Mã lớp HP [Mã Lớp HP]:
1. [Họ và Tên SV 1] - MSSV: [MSSV 1] - Lớp: [Mã Lớp] (Hiện thuộc Nhóm [X])
2. [Họ và Tên SV 2] - MSSV: [MSSV 2] - Lớp: [Mã Lớp] (Hiện thuộc Nhóm [Y])

Chúng em viết thư này kính xin Thầy/Cô cho phép chúng em được hoán đổi vị trí thành viên cho nhau (Bạn [Tên SV 1] sang Nhóm [Y], và Bạn [Tên SV 2] về Nhóm [X]).

Lý do: [Ví dụ: Nhóm [X] đang làm đề tài về Lập trình Android bằng Kotlin, phù hợp với thế mạnh chuyên môn của bạn [Tên SV 2]; trong khi Nhóm [Y] làm về Flutter/Cross-platform phù hợp với kinh nghiệm của bạn [Tên SV 1]. Việc đổi nhóm sẽ giúp hai nhóm tối ưu năng lực và triển khai bài tập lớn tốt nhất.]

Sự việc đã được sự nhất trí 100% của toàn bộ thành viên cả hai Nhóm [X] và [Y].

Chúng em kính mong nhận được sự đồng thuận từ Thầy/Cô. Chúng em xin chân thành cảm ơn Thầy/Cô!

Đồng kính thư,
[Họ và Tên SV 1] - MSSV: [MSSV 1] - Lớp: [Mã Lớp]
[Họ và Tên SV 2] - MSSV: [MSSV 2] - Lớp: [Mã Lớp]
```

---

### Phần 4: Hỏi Bài / Trao Đổi Kiến Thức / Xin Lịch Hẹn Tư Vấn

#### Mẫu 4.1: Hỏi đáp thắc mắc bài giảng / Lỗi lập trình / Đồ án
```text
Tiêu đề: [Tên Môn Học] - [Mã Lớp HP] - [MSSV] - [Họ và Tên] - Thắc mắc về [Tên chủ đề / Lỗi gặp phải]

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Em tên là: [Họ và Tên]
Mã số sinh viên: [MSSV]
Sinh viên lớp: [Mã Lớp], hiện đang học môn [Tên Môn Học] (Mã học phần: [Mã Lớp HP]).

Trong quá trình học và làm bài tập [tên bài tập / slide bài giảng số...], em có gặp một vấn đề chưa hiểu rõ và muốn kính nhờ Thầy/Cô giải đáp giúp em ạ.

Cụ thể thắc mắc của em như sau:
1. Mô tả vấn đề: [Trình bày ngắn gọn, mạch lạc câu hỏi hoặc hành vi lỗi gặp phải].
2. Các bước em đã tự tìm hiểu: [Nêu các cách đã thử, ví dụ: em đã đọc lại slide trang ..., đã tra cứu tài liệu chính thức nhưng vẫn gặp hiện tượng ...].
3. Câu hỏi cụ thể: [Câu hỏi trọng tâm muốn nhờ Thầy/Cô giải đáp].

(Nếu có code/ảnh lỗi: Em xin gửi kèm ảnh chụp màn hình thông báo lỗi và đoạn mã nguồn liên quan ở file đính kèm để Thầy/Cô tiện quan sát ạ.)

Em biết Thầy/Cô rất bận rộn, em rất mong khi có thời gian thuận tiện Thầy/Cô có thể hướng dẫn thêm cho em. Em xin chân thành cảm ơn Thầy/Cô!

Kính chúc Thầy/Cô một ngày làm việc hiệu quả.

Kính thư,
[Họ và Tên]
MSSV: [MSSV]
Lớp: [Mã Lớp]
```

#### Mẫu 4.2: Xin lịch hẹn gặp trực tiếp hoặc online (Office Hours / Hướng dẫn đồ án)
```text
Tiêu đề: [Tên Môn Học] - [Mã Lớp HP] - [MSSV] - [Họ và Tên] - Xin lịch hẹn gặp trao đổi về [Đồ án/Bài học]

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Em tên là: [Họ và Tên]
Mã số sinh viên: [MSSV]
Sinh viên lớp: [Mã Lớp], hiện đang theo học lớp học phần [Tên Môn Học] do Thầy/Cô phụ trách.

Hiện tại nhóm em / em đang thực hiện [Đồ án môn học / Nghiên cứu đề tài...] và có một số vấn đề về kiến trúc hệ thống cũng như giải thuật cần xin ý kiến định hướng từ Thầy/Cô.

Do nội dung cần trao đổi trực tiếp để Thầy/Cô góp ý trên sơ đồ thiết kế, em viết thư này kính xin phép Thầy/Cô cho em/nhóm em một buổi hẹn ngắn khoảng 15-20 phút (trực tiếp tại văn phòng khoa hoặc qua Google Meet/MS Teams).

Các khung giờ em/nhóm em có thể tham gia gặp Thầy/Cô:
- Khung giờ 1: [Ví dụ: Chiều Thứ 3, từ 14:00 đến 17:00]
- Khung giờ 2: [Ví dụ: Sáng Thứ 5, từ 09:00 đến 11:30]

Thầy/Cô có thể sắp xếp vào khung giờ nào thuận tiện nhất cho Thầy/Cô ạ? Nếu các khung giờ trên chưa phù hợp, em xin phép lắng nghe lịch hẹn theo sự sắp xếp của Thầy/Cô.

Em xin chân thành cảm ơn Thầy/Cô!

Kính thư,
[Họ và Tên]
MSSV: [MSSV]
Lớp: [Mã Lớp]
Số điện thoại: [Số điện thoại]
```

---

## 5. Quy Chuẩn Văn Phong Giao Tiếp Học Đường

Khi soạn nội dung cho người dùng, Agent cần nhắc nhở hoặc đảm bảo các tiêu chuẩn sau:

1. **Email gửi đi:** Khuyến khích sử dụng email sinh viên do nhà trường cấp (`@sinhvien...edu.vn` hoặc `@student...edu.vn`) để tăng độ tin cậy và tránh bị rơi vào hộp thư rác (Spam).
2. **Tiêu đề thư (Subject):** Tuyệt đối không để trống, luôn bắt đầu bằng `[Mã môn/Tên môn] - [Mã lớp] - [MSSV] - [Họ tên] - [Mục đích]`.
3. **Xưng hô:** Giữ thái độ lễ phép, trang trọng (*"Kính gửi Thầy/Cô...", "Em tên là...", "Em xin chân thành cảm ơn Thầy/Cô"*).
4. **Trình bày:** Rõ ràng, xuống dòng rành mạch, không viết tắt, không dùng emoji hoặc ngôn ngữ mạng.
5. **Lý do & Minh chứng:** Luôn trung thực và kèm theo tài liệu xác thực (nếu có: giấy khám bệnh, ảnh chụp lỗi, báo cáo tiến độ, minh chứng trùng lịch...).

---

## 6. Lưu và Xuất Tệp (Tùy Chọn)

Khi người dùng yêu cầu lưu lại văn bản thành tệp:
- Lưu tệp vào thư mục `D:\Output_skill/` (hoặc thư mục sinh viên chỉ định).
- Định dạng tên tệp:
  - Xin nghỉ: `don_nghi_<MSSV>_<YYYY-MM-DD>.md`
  - Gia hạn deadline: `xin_gia_han_<MSSV>_<YYYY-MM-DD>.md`
  - Đổi nhóm: `xin_doi_nhom_<MSSV>_<YYYY-MM-DD>.md`
  - Hỏi bài: `hoi_bai_<MSSV>_<YYYY-MM-DD>.md`
- Trả về đường dẫn tệp kèm bản xem trước hoàn chỉnh cho người dùng.

