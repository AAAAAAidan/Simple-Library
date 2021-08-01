<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
  <h2>Browse the books!</h2>
  <table>
    <tr class="grid-3">
      <td><i>The Hobbit</i> by J.R.R. Tolkien</td>
      <td><i>Good Night, Gorilla</i> by Peggy Rathman</td>
      <td><i>Sneeze</i> by Naoki Urasawa</td>
    </tr>
    <tr class="grid-3">
      <td><img src="<c:url value="resources/images/thehobbit.jpg" />" alt="Book cover of The Hobbit by J.R.R. Tolkien" /></td>
      <td><img src="<c:url value="resources/images/goodnightgorilla.jpg" />" alt="Book cover of Good Night, Gorilla by Peggy Rathman" /></td>
      <td><img src="<c:url value="resources/images/sneeze.jpg" />" alt="Book cover of Sneeze by Naoki Urasawa" /></td>
    </tr>
  </table>
</div>
<div>
  <h2>Here's some video!</h2>
  <table>
    <tr class="grid-2">
      <td>"An Open Book" by Joel Dunn</td>
      <td>"Piles And Layers Of Book For Reading Placed In A Book Shelves" by Matthias Groeneveld</td>
    </tr>
    <tr class="grid-2">
      <td>
        <video controls>
          <source src="<c:url value="resources/images/anopenbook.mp4" />" type="video/mp4">
        </video>
      </td>
      <td><img src="<c:url value="resources/images/pilesofbooks.gif" />" alt="" /></td>
    </tr>
  </table>
</div>
